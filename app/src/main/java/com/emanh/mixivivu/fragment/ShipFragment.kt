package com.emanh.mixivivu.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.emanh.mixivivu.R
import com.emanh.mixivivu.databinding.EditInputSpinnerBinding
import com.emanh.mixivivu.databinding.FragmentShipBinding

class ShipFragment : Fragment() {
    private var _binding: FragmentShipBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShipBinding.inflate(inflater, container, false)

        initVideoIntro()
        initInputSpinner()

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

    private fun setupInputSpinner(item: InputSpinnerItem) {
        (item.binding.label as? AutoCompleteTextView)?.apply {
            setAdapter(item.adapter)
            item.initialSelection?.let { setText(it, false) }
            setOnItemClickListener { _, _, position, _ ->
                setText(item.adapter.getItem(position), false)
            }
        }
    }

    private data class InputSpinnerItem(
        val binding: EditInputSpinnerBinding,
        val adapter: ArrayAdapter<String>,
        val initialSelection: String?
    )
}