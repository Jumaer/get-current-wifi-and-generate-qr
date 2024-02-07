package com.example.wifi_qr.dialog


import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import com.example.wifi_qr.R
import com.example.wifi_qr.databinding.LayoutBsQrDisplayBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


class BsQrImage(context: Context, bitmap: Bitmap, val listener: OnClickListener) {
    private var binding: LayoutBsQrDisplayBinding
    private var bottomSheet: BottomSheetDialog

    init {
        binding =
            LayoutBsQrDisplayBinding.inflate(
                LayoutInflater.from(context),
                null,
                false
            ).apply {

                btnCancel.setOnClickListener {
                    dismiss()
                    listener.onCancel()
                }
                imgNoData.setImageBitmap(bitmap)

            }
        bottomSheet = BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme).apply {
            setContentView(binding.root)
        }
    }


    fun show() {
        if (!bottomSheet.isShowing) bottomSheet.show()
    }

    private fun dismiss() {
        if (bottomSheet.isShowing) bottomSheet.dismiss()
    }




    interface OnClickListener {
        fun onCancel()
    }


}