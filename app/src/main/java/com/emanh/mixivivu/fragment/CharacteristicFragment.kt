@file:Suppress("DEPRECATION")

package com.emanh.mixivivu.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.emanh.mixivivu.adapter.HighlightsAdapter
import com.emanh.mixivivu.databinding.FragmentCharacteristicBinding
import com.emanh.mixivivu.model.ShipModel
import com.emanh.mixivivu.viewModel.ShipViewModel

class CharacteristicFragment : Fragment() {
    private var _binding: FragmentCharacteristicBinding? = null

    private val binding get() = _binding!!

    private lateinit var ship: ShipModel
    private lateinit var shipViewModel: ShipViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacteristicBinding.inflate(inflater, container, false)

        initListHighlights()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListHighlights() {
        ship = requireActivity().intent.getParcelableExtra("objectShip")!!
        shipViewModel = ViewModelProvider(this)[ShipViewModel::class.java]
        shipViewModel.loadShip()

        val listHighlights =  ArrayList<String>()
        for (highlight in ship.characteristic) {
            listHighlights.add(highlight)
        }

        binding.progressHighlights.visibility = View.VISIBLE
        shipViewModel.ship.observe(viewLifecycleOwner) {
            binding.listHighlights.layoutManager = LinearLayoutManager(requireContext())
            binding.listHighlights.adapter = HighlightsAdapter(listHighlights)
            binding.progressHighlights.visibility = View.GONE
        }
    }
}