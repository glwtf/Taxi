package com.example.taxi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.taxi.data.OrdersRepositoryImpl
import com.example.taxi.domain.GetOrderItemUseCase

class OrderItemViewModel : ViewModel() {

    private val repository = OrdersRepositoryImpl

    private val getOrderItemUseCase = GetOrderItemUseCase(repository)

    fun getShopItem(itemId: Int) = getOrderItemUseCase(itemId)
}