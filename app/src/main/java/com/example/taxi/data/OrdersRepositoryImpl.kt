package com.example.taxi.data

import androidx.lifecycle.MutableLiveData
import com.example.taxi.domain.Order
import com.example.taxi.domain.OrdersRepository
import java.text.SimpleDateFormat
import java.util.*

object OrdersRepositoryImpl : OrdersRepository {

    private val ldOrders = MutableLiveData<List<Order>>()
    private var orderList = sortedSetOf<Order>({ item1, item2 ->
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'HH:MM");
        val date1 = sdf.parse(item1.orderTime)
        val date2 = sdf.parse(item2.orderTime)
        date2!!.compareTo(date1)
    })

    override fun getOrderList() = ldOrders

    override fun getOrderItem(itemId: Int) = orderList.find{ item -> item.id == itemId }

    override suspend fun loadOrders() {
        val orders = LoadOrders()
        orders().forEach { item ->
            orderList.add(item)
        }
        ldOrders.value = orderList.toList()
    }

    override suspend fun loadImageFromNetwork(imageName : String) {
        val imageUrl = LoadImage()
        imageUrl.loadImageFromNetwork(imageName)
    }
    
}

