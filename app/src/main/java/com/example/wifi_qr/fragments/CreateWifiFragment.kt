package com.example.wifi_qr.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.wifi_qr.WifiQrBaseActivity
import com.example.wifi_qr.databinding.FragmentCreateWifiBinding
import com.example.wifi_qr.util.Communicator


class CreateWifiFragment : Fragment() {
    private lateinit var binding : FragmentCreateWifiBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateWifiBinding.inflate(inflater, container, false)
        setViews()
        return binding.root
    }

    private fun setViews() {
        binding.apply {

            imgLogoHint.setOnClickListener {
                setLogoUri()
            }




        }
    }

    private fun setLogoUri() {
        (activity as WifiQrBaseActivity).showImgBs(object : Communicator{
            override fun onCatchUri(uri: Uri) {
                binding.imgLogoHint.setImageURI(uri)
            }
        })
    }



//            generateQr(etWifiName.editText,
//                etWifiPass.editText,
//                etNetworkType.editText)
    private fun generateQr(
        wifiName: EditText?,
        netType: EditText?,
        wifiNetType: EditText?) {

        val nName = wifiName?.text.toString()
        val nNetType = netType?.text.toString()
        val nPassword = wifiNetType?.text.toString()

        (activity as WifiQrBaseActivity).generateQr(nName,nNetType, nPassword )


    }
}