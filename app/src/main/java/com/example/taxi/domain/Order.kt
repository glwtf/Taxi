package com.example.taxi.domain

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id : Int,
    val startAddress : Address,
    val endAddress : Address,
    val price: Price,
    val orderTime : String,
    val vehicle : Vehicle
)
{
    @Serializable
    data class Address (
        val city : String,
        val address : String
    )

    @Serializable
    data class Price (
        val amount : Int,
        val currency : String
    )

    @Serializable
    data class Vehicle (
        val regNumber : String,
        val modelName : String,
        val photo : String,
        val driverName : String
    )
}
