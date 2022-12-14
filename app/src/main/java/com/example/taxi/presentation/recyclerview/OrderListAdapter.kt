package com.example.taxi.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.taxi.R
import com.example.taxi.databinding.ItemTaxiOrderBinding
import com.example.taxi.domain.Order
import java.text.SimpleDateFormat
import java.util.*

class OrderListAdapter() : ListAdapter<
        Order,
        OrderItemViewHolder
        >(OrderItemDiffCallBack())
{
    var onShopItemClickListener : ((Order) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val binding = ItemTaxiOrderBinding.inflate (
        LayoutInflater.from(parent.context),
        parent,
        false
    )
        return OrderItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) { //set data in view
        val order = getItem(position)
        val binding = holder.binding
        val date = formatData(order.orderTime)
        val startAddress = "${order.startAddress.city} ${order.startAddress.address}"
        val endAddress = "${order.endAddress.city} ${order.endAddress.address}"
        val price = "${order.price.amount / 100} ${order.price.currency}"

        binding.tvStartAddress.text = startAddress
        binding.tvEndAddress.text = endAddress
        binding.tvOrderTime.text = date
        binding.tvPrice.text = price
        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(order)
        }
    }

    private fun formatData(dateString : String) : String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        val outFormat = SimpleDateFormat("dd/MM/yyyy");
        val originDate = originalFormat.parse(dateString)
        return outFormat.format(originDate)
    }
}