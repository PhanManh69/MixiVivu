@file:Suppress("DEPRECATION")

package com.emanh.mixivivu.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.emanh.mixivivu.databinding.FragmentIntroBinding
import com.emanh.mixivivu.model.ShipModel
import com.emanh.mixivivu.viewModel.ShipViewModel

class IntroFragment : Fragment() {
    private var _binding: FragmentIntroBinding? = null

    private val binding get() = _binding!!

    private lateinit var ship: ShipModel
    private lateinit var shipViewModel: ShipViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)

        initIntro()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initIntro() {
        ship = requireActivity().intent.getParcelableExtra("objectShip")!!
        shipViewModel = ViewModelProvider(this)[ShipViewModel::class.java]
        shipViewModel.loadShip("", "", Int.MIN_VALUE, Int.MAX_VALUE)

        binding.progressIntro.visibility = View.VISIBLE
        shipViewModel.ship.observe(viewLifecycleOwner) {
            binding.introContent1.text = ship.introduceText[0]
            binding.introContent2.text = ship.introduceText[1]
            binding.introContent3.text = ship.introduceText[2]

            Glide.with(this)
                .load(ship.introducePicUrl[0])
                .into(binding.introPic1)

            Glide.with(this)
                .load(ship.introducePicUrl[1])
                .into(binding.introPic2)

            Glide.with(this)
                .load(ship.introducePicUrl[2])
                .into(binding.introPic3)

            binding.introContent1.visibility = View.VISIBLE
            binding.introContent2.visibility = View.VISIBLE
            binding.introContent3.visibility = View.VISIBLE
            binding.introPic1.visibility = View.VISIBLE
            binding.introPic2.visibility = View.VISIBLE
            binding.introPic3.visibility = View.VISIBLE
            binding.progressIntro.visibility = View.GONE
        }
    }
}