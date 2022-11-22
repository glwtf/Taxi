package com.example.taxi.data

import android.os.Environment
import android.util.Log
import android.widget.ImageView
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.io.File

class LoadImage {

    suspend fun loadImageFromNetwork(imageName : String) {
        val client = HttpClient(Android)
        val url = Url(IMAGE_URL_PREFIX+imageName)
        val file = File(IMAGE_DOWNLOAD_PATH_PREFIX+imageName)
        client.get(url).bodyAsChannel().copyAndClose(file.writeChannel())
        client.close()
    }

    companion object {
        private const val IMAGE_URL_PREFIX = "https://www.roxiemobile.ru/careers/test/images/"
        private const val IMAGE_DOWNLOAD_PATH_PREFIX = "/sdcard/Download/"
    }
}