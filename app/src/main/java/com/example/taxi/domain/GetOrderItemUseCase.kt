package com.example.taxi.domain

import javax.inject.Inject

class GetOrderItemUseCase @Inject constructor(private val repository: OrdersRepository) {
    operator fun invoke( itemId : Int )
            = repository.getOrderItem(itemId)
}