package com.example.taxi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taxi.data.OrdersRepositoryImpl
import com.example.taxi.domain.GetOrderListUseCase
import com.example.taxi.domain.LoadOrdersUseCase
import kotlinx.coroutines.launch

class MainFragmentViewModel : ViewModel() {

    private val repository = OrdersRepositoryImpl

    private val getOrderListUseCase = GetOrderListUseCase(repository)
    private val loadOrdersUseCase = LoadOrdersUseCase(repository)

    init {
        viewModelScope.launch {
            loadOrdersUseCase()
        }
    }

    val ldOrders = getOrderListUseCase()


}