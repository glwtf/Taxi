package com.example.taxi.domain

import javax.inject.Inject

class LoadOrdersUseCase @Inject constructor(private val repository: OrdersRepository) {
    suspend operator fun invoke() = repository.loadOrders()
}