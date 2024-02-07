package com.example.wifi_qr.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

object ImageUtils {

    /**
     * this function is using convert image uri to bitmap
     * @param[context] context of view
     * @param[uri] Uri that contains image
     * @return[Bitmap] image
     */
    fun getBitmap(
        context: Context,
        uri: Uri
    ): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri)).copy(
                Bitmap.Config.RGBA_F16, true)
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri).copy(Bitmap.Config.RGBA_F16, true)
        }
    }
}