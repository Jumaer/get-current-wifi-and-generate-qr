package com.example.wifi_qr.network

import com.example.wifi_qr.constants.Constants.NET_TYPE_NO_ENC
import com.example.wifi_qr.constants.Constants.NET_TYPE_WEP
import com.example.wifi_qr.constants.Constants.NET_TYPE_WPA
import com.example.wifi_qr.constants.Constants.NET_TYPE_WPA2

object NetworkTypeUtils {
    val netTypeMap = mutableMapOf<Int,Boolean>()
    private lateinit var networkType : MutableList<String>
    fun getNetworkType(): MutableList<String> {
        if(!::networkType.isInitialized){
            networkType = mutableListOf(
                NET_TYPE_WEP,
                NET_TYPE_WPA,
                NET_TYPE_WPA2,
                NET_TYPE_NO_ENC
            )
        }
        return networkType
    }
}