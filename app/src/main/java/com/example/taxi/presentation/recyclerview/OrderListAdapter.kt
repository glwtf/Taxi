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

        binding.tvStartAddress.text = "${order.startAddress.city} ${order.startAddress.address}"
        binding.tvEndAddress.text = "${order.endAddress.city} ${order.endAddress.address}"
        binding.tvOrderTime.text = date
        binding.tvPrice.text = "${order.price.amount / 100} ${order.price.currency}"
        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(order)
        }

    }

    private fun formatData(dateString : String) : String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        val outFormat = SimpleDateFormat("yyyy-MM-dd");
        val originDate = originalFormat.parse(dateString)
        return outFormat.format(originDate)
    }

    companion object {
        const val VIEW_TYPE_DISABLED = 2
        const val MAX_POOL_SIZE = 15
    }
}