package com.example.wifi_qr.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wifi_qr.databinding.FragmentUpdateWifiBinding


class UpdateWifiFragment : Fragment() {
    private lateinit var binding : FragmentUpdateWifiBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateWifiBinding.inflate(inflater, container, false)

        return binding.root
    }
}