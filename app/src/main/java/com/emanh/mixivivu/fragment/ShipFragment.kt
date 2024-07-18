package com.emanh.mixivivu.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.emanh.mixivivu.R
import com.emanh.mixivivu.adapter.ShipAdapter
import com.emanh.mixivivu.databinding.FragmentShipBinding
import com.emanh.mixivivu.model.InputSpinnerItem
import com.emanh.mixivivu.viewModel.ShipViewModel

class ShipFragment : Fragment() {
    private var _binding: FragmentShipBinding? = null

    private val binding get() = _binding!!
    private val viewModel = ShipViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShipBinding.inflate(inflater, container, false)

        initVideoIntro()
        initInputSpinner()
        initShip("", "", Int.MIN_VALUE, Int.MAX_VALUE)
        initSearch()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initVideoIntro() {
        val videoPath = "android.resource://" + context?.packageName + "/" + R.raw.mixivivu_ship
        binding.videoIntro.setVideoURI(Uri.parse(videoPath))
        binding.videoIntro.setOnPreparedListener {mediaPlayer ->
            mediaPlayer.isLooping = true
            binding.videoIntro.start()
        }
    }

    private fun initInputSpinner() {
        val listDestination = resources.getStringArray(R.array.filterDestination)
        val arrayAdapterDestination = ArrayAdapter(requireContext(), R.layout.spinner_item, listDestination)

        val listPrice = resources.getStringArray(R.array.filterPrice)
        val arrayAdapterPrice = ArrayAdapter(requireContext(), R.layout.spinner_item, listPrice)

        val inputSpinnerItems = listOf(
            InputSpinnerItem(binding.filterDestination, arrayAdapterDestination, listDestination.firstOrNull()),
            InputSpinnerItem(binding.filterPrice, arrayAdapterPrice, listPrice.firstOrNull())
        )

        inputSpinnerItems.forEach { item ->
            setupInputSpinner(item)
        }
    }

    private fun initShip(inputShip: String, location: String, minPrice: Int, maxPrice: Int) {
        viewModel.loadShip(inputShip, location, minPrice, maxPrice)

        binding.progressShip.visibility = View.VISIBLE
        viewModel.ship.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.noData.visibility = View.VISIBLE
                binding.listShip.visibility = View.GONE
            } else {
                binding.noData.visibility = View.GONE
                binding.listShip.visibility = View.VISIBLE

                binding.listShip.layoutManager = LinearLayoutManager(requireContext())
                binding.listShip.adapter = ShipAdapter(it)
            }
            binding.progressShip.visibility = View.GONE
        }
    }

    private fun initSearch() {
        binding.searchShip.setOnClickListener {
            var minPrice = 0
            var maxPrice = 0

            var selectedDestination = (binding.filterDestination.label as? AutoCompleteTextView)?.text.toString()
            if (selectedDestination == "Tất cả địa điểm") selectedDestination = ""

            when ((binding.filterPrice.label as? AutoCompleteTextView)?.text.toString()) {
                "Tất cả mức giá" -> {
                    minPrice = Int.MIN_VALUE
                    maxPrice = Int.MAX_VALUE
                }
                "Từ 1tr đến 3tr" -> {
                    minPrice = 1000000
                    maxPrice = 3000000
                }
                "Từ 3tr đến 6tr" -> {
                    minPrice = 3000000
                    maxPrice = 6000000
                }
                "Trên 6tr" -> {
                    minPrice = 6000000
                    maxPrice = Int.MAX_VALUE
                }
            }

            initShip(binding.inputShip.text.toString(), selectedDestination, minPrice, maxPrice)
        }
    }

    private fun setupInputSpinner(item: InputSpinnerItem) {
        (item.binding.label as? AutoCompleteTextView)?.apply {
            setAdapter(item.adapter)
            item.initialSelection?.let { setText(it, false) }
            setOnItemClickListener { _, _, position, _ ->
                setText(item.adapter.getItem(position), false)
            }
        }
    }
}