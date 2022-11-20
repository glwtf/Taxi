package com.example.taxi.presentation.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.taxi.domain.Order

class OrderItemDiffCallBack : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Order, newItem: Order) = oldItem == newItem
}