package com.example.taxi.domain

import androidx.lifecycle.LiveData
import java.io.File

interface OrdersRepository {

    fun getOrderList() : LiveData<List<Order>>
    fun getOrderItem(itemId : Int): Order?
    suspend fun loadOrders()
    suspend fun loadImageFromNetwork(imageName : String, dirPath : String)
}