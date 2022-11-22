package com.example.taxi.data

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.example.taxi.domain.Order
import com.example.taxi.domain.OrdersRepository

object OrdersRepositoryImpl : OrdersRepository {

    private val ldOrders = MutableLiveData<List<Order>>()
    private var orderList = listOf<Order>()

    override fun getOrderList() = ldOrders

    override fun getOrderItem(itemId: Int) = orderList.find{ item -> item.id == itemId }

    override suspend fun loadOrders() {
        val orders = LoadOrders()
        orderList = orders()
        ldOrders.value = orderList
    }

    override suspend fun loadImageFromNetwork(imageName : String) {
        val imageUrl = LoadImage()
        imageUrl.loadImageFromNetwork(imageName)
    }
}

