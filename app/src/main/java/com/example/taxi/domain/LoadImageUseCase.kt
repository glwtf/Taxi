package com.example.taxi.domain

import javax.inject.Inject

class LoadImageUseCase @Inject constructor(private val repository: OrdersRepository) {

    suspend fun loadImageFromNetwork(imageName : String, dirPath : String)
        = repository.loadImageFromNetwork(imageName, dirPath)
}