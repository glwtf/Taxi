package com.example.taxi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.taxi.data.OrdersRepositoryImpl
import com.example.taxi.domain.GetOrderItemUseCase
import com.example.taxi.domain.LoadImageUseCase
import java.io.File

class OrderItemViewModel : ViewModel() {

    private val repository = OrdersRepositoryImpl

    private val getOrderItemUseCase = GetOrderItemUseCase(repository)
    private val loadImageUseCase = LoadImageUseCase(repository)

    fun getShopItem(itemId: Int) = getOrderItemUseCase(itemId)

    suspend fun loadImageFromNetwork(imageName : String) {
        loadImageUseCase.loadImageFromNetwork(imageName)
    }

    fun checkImageOnDevice(imagePath : String) : Boolean{
        val file = File(imagePath)
        return file.exists()
    }
}