package com.example.wifi_qr.util

import android.Manifest

object WifiPermissionUtils {
    /**
     * Array of manifest permissions
     */
    val listWifiPermission = arrayOf(
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private var isAllPermissionGranted = false
    /**
     * Check all permissions has been added or not ..
     * @return [Boolean] for result of success ..
     * @param permissions is the map of success results according permissions ...
     */
    fun checkPermissionSuccess(permissions: Map<String, Boolean>): Boolean {
        isAllPermissionGranted = true
        permissions.values.forEach {
            if (!it) {
                isAllPermissionGranted = false
            }
        }
        return isAllPermissionGranted
    }



}