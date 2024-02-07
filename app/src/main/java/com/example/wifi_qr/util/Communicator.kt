package com.example.wifi_qr.util

import android.graphics.Bitmap
import android.net.Uri

interface Communicator {
    fun onCatchUri(uri: Uri)
    fun onShowQr(bitmap: Bitmap)
}