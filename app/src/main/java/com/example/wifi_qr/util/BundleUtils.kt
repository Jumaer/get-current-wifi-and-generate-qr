package com.example.wifi_qr.util

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import java.io.Serializable

object BundleUtils {

    fun <T : Serializable?> getSerializable(bundle: Bundle?, key: String, clazz: Class<T>): T? {
        return if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) bundle?.getSerializable(key, clazz)
        else bundle?.getSerializable(key) as T
    }


}