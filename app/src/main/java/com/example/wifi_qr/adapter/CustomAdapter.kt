package com.example.wifi_qr.adapter


import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.wifi_qr.R
import com.example.wifi_qr.network.NetworkTypeUtils.netTypeMap

class CustomAdapter(
    val mContext: Context,
    val layout: Int,
    dataList: MutableList<String>
) : ArrayAdapter<String>(mContext, layout, dataList) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)

        if (view is TextView) {
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX,mContext.resources.getDimension(com.intuit.ssp.R.dimen._11ssp))
            view.includeFontPadding = false
            netTypeMap[position]?.let { setRow(it, view) }
        }
        return view
    }

    private fun setRow(isSelected: Boolean, view: TextView) {
        if (isSelected) {
            view.setTextColor(ContextCompat.getColor(mContext, R.color.black))
            view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.row_hint))

        } else {
            view.setTextColor(ContextCompat.getColor(mContext, R.color.text_clr))
            view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white))
        }

    }


}