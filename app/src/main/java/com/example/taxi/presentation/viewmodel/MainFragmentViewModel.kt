package com.example.taxi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taxi.data.OrdersRepositoryImpl
import com.example.taxi.domain.GetOrderListUseCase
import com.example.taxi.domain.LoadOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getOrderListUseCase : GetOrderListUseCase,
    private val loadOrdersUseCase : LoadOrdersUseCase,
) : ViewModel() {
    init {
        viewModelScope.launch {
            loadOrdersUseCase()
        }
    }

    val ldOrders = getOrderListUseCase()


}