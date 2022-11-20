package com.example.taxi.presentation.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taxi.R

class OrderItemViewHolder(val view: View): RecyclerView.ViewHolder(view) //class of viewHolder
{
    val tvStartAddress = view.findViewById<TextView>(R.id.tv_start_address)
    val tvEndAddress = view.findViewById<TextView>(R.id.tv_end_address)
    val tvOrderTime = view.findViewById<TextView>(R.id.tv_order_time)
    val tvPrice = view.findViewById<TextView>(R.id.tv_price)
}