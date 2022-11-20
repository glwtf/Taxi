package com.example.taxi.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.taxi.R
import com.example.taxi.domain.Order

class OrderListAdapter() : ListAdapter<
        Order,
        OrderItemViewHolder
        >(OrderItemDiffCallBack())
{
    var onShopItemClickListener : ((Order) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder { //create view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_taxi_order, parent, false)
        return OrderItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) { //set data in view
        val order = getItem(position)
        holder.tvStartAddress.text = "${order.startAddress.city} ${order.startAddress.address}"
        holder.tvEndAddress.text = "${order.endAddress.city} ${order.endAddress.address}"
        holder.tvOrderTime.text = order.orderTime
        holder.tvPrice.text = "${order.price.amount / 100} ${order.price.currency}"
        holder.view.setOnClickListener {
            onShopItemClickListener?.invoke(order)
        }

    }

    companion object {
        const val VIEW_TYPE_DISABLED = 2
        const val MAX_POOL_SIZE = 15
    }
}