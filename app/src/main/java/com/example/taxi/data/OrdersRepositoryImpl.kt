package com.example.taxi.data

import com.example.taxi.domain.Order
import com.example.taxi.domain.OrdersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor() : OrdersRepository {

    private val ldOrders = MutableStateFlow<List<Order>>(listOf())
    private var orderList = listOf<Order>()

    override fun getOrderList() = ldOrders

    override fun getOrderItem(itemId: Int) = orderList.find{ item -> item.id == itemId }

    override suspend fun loadOrders() {
        val orders = LoadOrders()
        orderList = orders().sortedByDescending { item ->
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'HH:MM");
            sdf.parse(item.orderTime)
        }
        ldOrders.value = orderList
    }

    override suspend fun loadImageFromNetwork(imageName : String, dirPath : String) {
        val imageUrl = LoadImage()
        imageUrl.loadImageFromNetwork(imageName,dirPath)
    }
    
}

