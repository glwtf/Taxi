package com.example.taxi.domain

class GetOrderItemUseCase (private val repository: OrdersRepository) {
    operator fun invoke( itemId : Int )
            = repository.getOrderItem(itemId)
}