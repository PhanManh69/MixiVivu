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
        initShip()

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

    private fun initShip() {
        binding.progressShip.visibility = View.VISIBLE
        viewModel.ship.observe(viewLifecycleOwner) {
            binding.listShip.layoutManager = LinearLayoutManager(requireContext())
            binding.listShip.adapter = ShipAdapter(it)
            binding.progressShip.visibility = View.GONE
        }

        viewModel.loadShip()
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