@file:Suppress("DEPRECATION")

package com.emanh.mixivivu.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.emanh.mixivivu.adapter.PriceRoomAdapter
import com.emanh.mixivivu.databinding.FragmentPriceRoomBinding
import com.emanh.mixivivu.helper.OnOrderShipClickListener
import com.emanh.mixivivu.model.ShipModel
import com.emanh.mixivivu.viewModel.RoomViewModel
import java.text.NumberFormat
import java.util.Locale

class PriceRoomFragment : Fragment(), OnOrderShipClickListener {
    private var _binding: FragmentPriceRoomBinding? = null

    private val binding get() = _binding!!

    private lateinit var ship: ShipModel
    private lateinit var roomViewModel: RoomViewModel
    private var sumPrice: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPriceRoomBinding.inflate(inflater, container, false)

        initRoom()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    override fun onOrderShipClick(sumPriceRoom: Int) {
        this.sumPrice = sumPriceRoom
        binding.sumPrice.text = "${formatPrice(sumPriceRoom)} đ"
    }

    @SuppressLint("SetTextI18n")
    private fun initRoom() {
        ship = requireActivity().intent.getParcelableExtra("objectShip")!!
        roomViewModel = ViewModelProvider(this)[RoomViewModel::class.java]
        roomViewModel.loadRoom(ship.id.toString())

        binding.progressPriceRoom.visibility = View.VISIBLE
        roomViewModel.room.observe(viewLifecycleOwner) {
            binding.listPriceRoom.layoutManager = LinearLayoutManager(requireContext())
            binding.listPriceRoom.adapter = PriceRoomAdapter(it, this)
            binding.progressPriceRoom.visibility = View.GONE
        }

        binding.sumPrice.text = "${formatPrice(sumPrice)} đ"
    }

    private fun formatPrice(price: Int): String {
        val numberFormat = NumberFormat.getNumberInstance(Locale.US)
        return numberFormat.format(price)
    }
}