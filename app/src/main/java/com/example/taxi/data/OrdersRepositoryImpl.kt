package com.example.taxi.data

import androidx.lifecycle.MutableLiveData
import com.example.taxi.domain.Order
import com.example.taxi.domain.OrdersRepository
import java.text.SimpleDateFormat

object OrdersRepositoryImpl : OrdersRepository {

    private val ldOrders = MutableLiveData<List<Order>>()
    private var orderList = listOf<Order>()

    override fun getOrderList() = ldOrders

    override fun getOrderItem(itemId: Int) = orderList.find{ item -> item.id == itemId }

    override suspend fun loadOrders() {
        val orders = LoadOrders()
        orderList = orders().sortedByDescending { item ->
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'HH:MM");
            sdf.parse(item.orderTime)
        }
        ldOrders.value = orderList.toList()
    }

    override suspend fun loadImageFromNetwork(imageName : String, dirPath : String) {
        val imageUrl = LoadImage()
        imageUrl.loadImageFromNetwork(imageName,dirPath)
    }
    
}

