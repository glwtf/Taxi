package com.example.taxi.domain

import androidx.lifecycle.LiveData

interface OrdersRepository {

    fun getOrderList() : LiveData<List<Order>>
    fun getOrderItem(itemId : Int): Order?
    suspend fun loadOrders()
}