package com.example.taxi.domain

class GetOrderListUseCase (private val repository: OrdersRepository) {
    operator fun invoke()
            = repository.getOrderList()
}