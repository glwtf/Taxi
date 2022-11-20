package com.example.taxi.data

import com.example.taxi.domain.Order
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class LoadOrders {

    private val js = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(js)
        }
    }

    suspend operator fun invoke( ) : List<Order> = client.get(URL).body()

    companion object {
        const val URL = "https://www.roxiemobile.ru/careers/test/orders.json"
    }
}