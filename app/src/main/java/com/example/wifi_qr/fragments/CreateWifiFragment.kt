package com.example.wifi_qr.fragments

import android.R
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.widget.doAfterTextChanged
import com.example.wifi_qr.WifiQrBaseActivity
import com.example.wifi_qr.adapter.CustomAdapter
import com.example.wifi_qr.constants.Constants.NET_TYPE_NO_ENC
import com.example.wifi_qr.databinding.FragmentCreateWifiBinding
import com.example.wifi_qr.network.NetworkTypeUtils.getNetworkType
import com.example.wifi_qr.network.NetworkTypeUtils.netTypeMap
import com.example.wifi_qr.network.NetworkUtils
import com.example.wifi_qr.util.Communicator
import com.example.wifi_qr.util.WifiPermissionUtils.checkPermissionSuccess
import com.example.wifi_qr.util.WifiPermissionUtils.listWifiPermission


class CreateWifiFragment : Fragment() {

    private lateinit var binding : FragmentCreateWifiBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateWifiBinding.inflate(inflater, container, false)
        askForPermissions()
        setViews()
        return binding.root
    }

    private fun setViews() {
        binding.apply {
            imgLogoHint.setOnClickListener {
                showBsForLogo()
            }
        }
    }

    private fun showBsForLogo() {
        (activity as WifiQrBaseActivity).showImgBs(object : Communicator{
            override fun onCatchUri(uri: Uri) {
                binding.imgLogoHint.setImageURI(uri)
            }
        })
    }





    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>

    /**
     * Set listener from here ..
     * Ask for permissions for the result
     */
    private fun askForPermissions() {

        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (!checkPermissionSuccess(permissions)) {
                showPermissionsPopUp()
            } else{
                context?.let { NetworkUtils.setConnectedWifiName(it) }
            }
        }

        showPermissionsPopUp()
    }

    /**
     * This method will show permissions pop up
     * Before you perform the actual permission request, check whether your app
     * already has the permissions, and whether your app needs to show a permission
     * rationale dialog. For more details, see Request permissions.
     */
    private fun showPermissionsPopUp() {
        locationPermissionRequest.launch(
            listWifiPermission
        )
    }
}