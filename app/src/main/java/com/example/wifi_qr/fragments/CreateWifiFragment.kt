package com.example.wifi_qr.fragments


import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.widget.doAfterTextChanged
import com.example.wifi_qr.WifiQrBaseActivity
import com.example.wifi_qr.adapter.CustomAdapter
import com.example.wifi_qr.constants.Constants.NET_TYPE_NO_ENC
import com.example.wifi_qr.databinding.FragmentCreateWifiBinding
import com.example.wifi_qr.dialog.BsQrImage
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
        context?.let { setViews((activity as WifiQrBaseActivity), it) }

        return binding.root
    }

    private fun setViews(mActivity: WifiQrBaseActivity,context: Context) {
        binding.apply {
            setUpInitialView(false)
            etContainPassword.doAfterTextChanged {
                if (it != null) {
                    if (it.toString().isNotEmpty()) {
                        mPassword = it.toString().trim()
                    }
                }
                checkQrGeneratorCanBeEnable()
            }
            imgLogoHint.setOnClickListener {
                mActivity.showImgBs()
            }
            getWifiNameEditOptions(context)
            btnGenerateQr.setOnClickListener {
                mActivity.generateQr(networkName,netType, mPassword,)
            }
            mActivity.mListener = listenerInit(context)
        }
    }



    private fun listenerInit(context: Context): Communicator {
       return object : Communicator{
            override fun onCatchUri(uri: Uri) {
                binding.imgLogoHint.setImageURI(uri)
            }

            override fun onShowQr(bitmap: Bitmap) {
                   showQrBs(bitmap,context)
            }
        }
    }

    private fun showQrBs(bitmap: Bitmap, context: Context) {
        BsQrImage(context,bitmap,object : BsQrImage.OnClickListener{
            override fun onCancel() {

            }

        }).show()
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
                context?.let {
                    isAllPermissionGranted = true
                    NetworkUtils.setConnectedWifiName(it)
                }
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


    private fun setUpInitialView(isEnableView: Boolean) {
        binding.apply {
            btnGenerateQr.isEnabled = isEnableView
        }
        context?.let { setAllNetworkSpinnerData(it) }
    }


    private fun checkQrGeneratorCanBeEnable(): Boolean {
        if (netType.length > 2 && networkName.length > 2 && mPassword.length > 2) {
            binding.btnGenerateQr.isEnabled = true
            return true
        }
        if (netType.isEmpty()
            && binding.txtWifiNetType.text == NET_TYPE_NO_ENC
            && networkName.length > 2
        ) {
            binding.btnGenerateQr.isEnabled = true
            return true
        }
        binding.btnGenerateQr.isEnabled = false
        return false
    }


    private var netType = ""
    private var networkName = ""
    private var mPassword = ""

    private lateinit var typeWifiWindow: ListPopupWindow

    private fun setAllNetworkSpinnerData(mContext : Context) {

        val netTypeList = getNetworkType()
        if (setEncryptionTypeMapData()) {
            typeWifiWindow = ListPopupWindow(mContext, null, androidx.appcompat.R.attr.listPopupWindowStyle)
            setClickForSpinner()
            setViewToDisplayData(netTypeList,mContext)
        }
    }

    private fun setEncryptionTypeMapData(currentNetworkType : String = ""): Boolean {
        val netTypeList = getNetworkType()
        netTypeMap.clear()
        for (i in 0..<netTypeList.size){
            if(netTypeList[i] == currentNetworkType){
                netTypeMap.clear()
                netTypeMap[i] = true
                return true
            }
            netTypeMap[i] = false
        }
        return true
    }

    private fun setClickForSpinner() {
        binding.apply {
            txtWifiNetType.setOnClickListener {
                if (typeWifiWindow.isShowing) {
                    typeWifiWindow.dismiss()
                } else typeWifiWindow.show()
            }
            viewContainNetworkType.setOnClickListener {
                txtWifiNetType.performClick()
            }
        }
    }

    private fun setViewToDisplayData(netTypeList: MutableList<String> , mContext: Context) {
        typeWifiWindow.apply {
            anchorView = binding.txtWifiNetType
            setAdapter(CustomAdapter(mContext, android.R.layout.simple_list_item_1, netTypeList))
            setOnItemClickListener { _, _, position, _ ->
                if (setEncryptionTypeMapData()) {
                    setEncryption(position, netTypeList)
                    dismiss()
                }
            }
            setOnDismissListener {
                checkQrGeneratorCanBeEnable()
            }
        }
    }

    private fun setEncryption(position: Int, netTypeList: MutableList<String>) {

        netType = netTypeList[position]
        setEncryptionTypeMapData(netType)
        binding.txtWifiNetType.text = netType
        setEncryptionUi()
    }

    private fun setEncryptionUi() {
        binding.apply {
            netType = if (netType == NET_TYPE_NO_ENC) {
                mPassword = ""
                etContainPassword.setText(mPassword)
                setPassView(View.GONE)
                ""
            } else {

                setPassView(View.VISIBLE)
                netType
            }

        }
    }

    private fun setPassView(visible: Int) {
        binding.apply {
            etContainPassword.visibility = visible
            txtHintPassword.visibility = visible
        }
    }



    private val listOfWifi = mutableListOf<String>()
    private fun getWifiNameEditOptions(mContext: Context) {
        binding.apply {
            // Create adapter and add in AutoCompleteTextView

            val adapter = ArrayAdapter(
                mContext,
                android.R.layout.simple_list_item_1, listOfWifi
            )
            etContainNetworkName.setAdapter(adapter)
            etContainNetworkName.doAfterTextChanged {
                if (it != null) {
                    if (it.toString().isNotEmpty()) {
                        networkName = it.toString().trim()
                        checkQrGeneratorCanBeEnable()
                    }
                } else {
                    networkName = ""
                    checkQrGeneratorCanBeEnable()
                }

            }
            etContainNetworkName.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        getPopUpDataListForNetName()
                    }
                }

        }
    }

    private var isAllPermissionGranted = false

    private fun getPopUpDataListForNetName() {
        listOfWifi.clear()
        if (isAllPermissionGranted) {
            listOfWifi.add(NetworkUtils.connectedWifiName)
            binding.etContainNetworkName.showDropDown()
        }
    }
}