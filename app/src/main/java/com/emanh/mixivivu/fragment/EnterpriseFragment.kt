package com.emanh.mixivivu.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emanh.mixivivu.databinding.FragmentEnterpriseBinding

class EnterpriseFragment : Fragment() {
    private var _binding: FragmentEnterpriseBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterpriseBinding.inflate(inflater, container, false)

        initEnterprise()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initEnterprise() {
        binding.numberPhone.setOnClickListener {
            val phone = binding.numberPhone.text.toString()
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))

            startActivity(intent)
        }

        binding.email.setOnClickListener {
            val email = binding.email.text.toString()
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
            }
            startActivity(intent)
        }
    }
}