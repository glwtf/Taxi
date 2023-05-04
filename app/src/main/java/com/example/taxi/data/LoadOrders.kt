package com.example.taxi.data

import com.example.taxi.domain.Order
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.copyAndClose
import kotlinx.serialization.json.Json
import java.io.File

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

    suspend operator fun invoke( ) : List<Order> {
        val body : List<Order> = client.get(URL).body()
        client.close()
        return body
    }

    companion object {
        const val URL = "https://www.roxiemobile.ru/careers/test/orders.json"
    }
}