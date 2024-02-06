package com.example.wifi_qr.util

import android.net.Uri

interface Communicator {
    fun onCatchUri(uri: Uri)
}