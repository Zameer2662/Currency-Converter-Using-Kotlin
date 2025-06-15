package com.example.currencyconverter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class CurrencyAdapter(context: Context, private val currencyItems: List<CurrencyItem>) :
    ArrayAdapter<CurrencyItem>(context, 0, currencyItems) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
        val ivFlag = view.findViewById<ImageView>(R.id.ivFlag)
        val tvCurrency = view.findViewById<TextView>(R.id.tvCurrency)
        item?.let {
            ivFlag.setImageResource(it.flagResId)
            tvCurrency.text = it.currencyCode
        }
        return view
    }
}
