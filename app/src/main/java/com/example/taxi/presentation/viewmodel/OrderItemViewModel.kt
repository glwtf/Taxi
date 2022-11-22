package com.example.taxi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taxi.data.OrdersRepositoryImpl
import com.example.taxi.domain.GetOrderItemUseCase
import com.example.taxi.domain.LoadImageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderItemViewModel : ViewModel() {

    private val repository = OrdersRepositoryImpl

    private val getOrderItemUseCase = GetOrderItemUseCase(repository)
    private val loadImageUseCase = LoadImageUseCase(repository)

    fun getShopItem(itemId: Int) = getOrderItemUseCase(itemId)

    fun loadImageFromNetwork(imageName : String) {
        viewModelScope.launch(Dispatchers.IO) {
            loadImageUseCase.loadImageFromNetwork(imageName)
        }
    }

    fun checkImageOnDevice(imagePath : String) : Boolean{
        return false
    }
}