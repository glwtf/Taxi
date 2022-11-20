package com.example.taxi.domain

class LoadOrdersUseCase (private val repository: OrdersRepository) {
    suspend operator fun invoke()
            = repository.loadOrders()
}