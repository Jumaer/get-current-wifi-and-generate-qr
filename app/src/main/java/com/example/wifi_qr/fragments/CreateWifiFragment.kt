package com.example.wifi_qr.fragments

import android.Manifest
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.wifi_qr.WifiQrBaseActivity
import com.example.wifi_qr.constants.Constants
import com.example.wifi_qr.databinding.FragmentCreateWifiBinding
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