package com.example.taxi.domain

class LoadImageUseCase (private val repository: OrdersRepository) {

    suspend fun loadImageFromNetwork(imageName : String, dirPath : String)
        = repository.loadImageFromNetwork(imageName, dirPath)
}