package com.example.wifi_qr.dialog


import android.content.Context
import android.view.LayoutInflater
import com.example.wifi_qr.R
import com.example.wifi_qr.databinding.LayoutCapturePhotoBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class OpenImageDialog(context: Context, onCapture: () -> Unit,onFolder: () -> Unit) {


    private var binding : LayoutCapturePhotoBinding
    private var bottomSheet: BottomSheetDialog

    init {

        binding =
            LayoutCapturePhotoBinding.inflate(
                LayoutInflater.from(context),
                null,
                false
            ).apply {

                txtCapture.setOnClickListener {
                    onCapture()
                    dismiss()
                }
                txtFolder.setOnClickListener {
                    onFolder()
                    dismiss()
                }

            }
        bottomSheet = BottomSheetDialog(context, R.style.BottomSheetDialog).apply {
            setContentView(binding.root)
        }
    }

    fun show() {
        if (!bottomSheet.isShowing) bottomSheet.show()
    }

    private fun dismiss() {
        if (bottomSheet.isShowing) bottomSheet.dismiss()
    }

}