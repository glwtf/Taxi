package com.example.taxi.domain

import javax.inject.Inject

class GetOrderListUseCase @Inject constructor(private val repository: OrdersRepository) {
    operator fun invoke() = repository.getOrderList()
}