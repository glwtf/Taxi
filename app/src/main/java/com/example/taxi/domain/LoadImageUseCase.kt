package com.example.taxi.domain

class LoadImageUseCase (private val repository: OrdersRepository) {

    suspend fun loadImageFromNetwork(imageName : String)
        = repository.loadImageFromNetwork(imageName)
}