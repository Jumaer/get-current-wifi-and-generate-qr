package com.example.wifi_qr.network


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import androidx.core.content.ContextCompat
import com.example.wifi_qr.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

object WifiQrUtils {


    /**
     * generate wifi qr with this method ..
     * @param ssid is the exact name of network to generate qr ..
     * @param encryption is the network type encryption
     * @param password is the network pass
     * @param middleLogoBitmap is anything you want to set
     * @param context the fragmentContext ..
     * @param isShowMiddleIcon default true ---  to set icon ... can set false if required ..
     * @return [Bitmap] is the final result of QR to show
     */

    fun generateWifiQrCode(ssid: String,
                           encryption : String,
                           password:String ,
                           middleLogoBitmap: Bitmap?,
                           context: Context , isShowMiddleIcon : Boolean? = true
    ): Bitmap {
        val logoBitmap =  middleLogoBitmap ?: getDefaultBitmap(context)
        val size = 1200 //pixels
        val qrCodeContent = "WIFI:S:$ssid;T:$encryption;P:$password;;"
        val hints = hashMapOf<EncodeHintType, Int>().also {
            it[EncodeHintType.MARGIN] = 0

        }
        // Make the QR code buffer border narrower
        val bits = QRCodeWriter()
            .encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size,hints)
        val qrBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
        // no icon ..
        if(isShowMiddleIcon == false){
            return  qrBitmap
        }

        // something wrong to found default ..
        if(logoBitmap == null){
            return  qrBitmap
        }

        // custom or default icon will set ..
        return generateQrWithLogo(qrBitmap,logoBitmap)
    }


    /**
     * This method will generating qr with logo
     * @param qrcode is the exact bitmap of data
     * @param bitmap is the logo to set in middle ..
     * @return [Bitmap] is the final result of updated QR to show ..
     */
    private fun generateQrWithLogo(qrcode: Bitmap, bitmap: Bitmap): Bitmap {
        val combined =
            Bitmap.createBitmap(qrcode.width, qrcode.height, qrcode.config)
        val canvas = Canvas(combined)
        val canvasWidth = canvas.width
        val canvasHeight = canvas.height
        canvas.drawBitmap(qrcode, Matrix(), null)

        val resizeLogo =
            bitmap.let { Bitmap.createScaledBitmap(it, canvasWidth / 5, canvasHeight / 5, true) }
        val centreX = (canvasWidth - resizeLogo.width) / 2
        val centreY = (canvasHeight - resizeLogo.height) / 2
        val targetBmp: Bitmap = resizeLogo.copy(Bitmap.Config.ARGB_8888, false)
        canvas.drawBitmap(targetBmp, centreX.toFloat(), centreY.toFloat(), null)
        return combined
    }





    /**
     * GET DEFAULT BITMAP ...  IF ANY
     * given drawable -- R.drawable.ic_default_beeda_placeholder_qr --- can be replaced
     * @param mContext
     * @return [Bitmap] is the default bitmap to show ..
     */

    @Suppress("NAME_SHADOWING")
    private fun getDefaultBitmap(mContext : Context): Bitmap? {
        val drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_default_qr)!!
        return try {
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        } catch (e: OutOfMemoryError) {
            // Handle the error
            null
        }
    }



}