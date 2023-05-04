package com.example.taxi.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager
import coil.load
import com.example.taxi.data.OrdersRepositoryImpl
import com.example.taxi.domain.GetOrderItemUseCase
import com.example.taxi.domain.LoadImageUseCase
import com.example.taxi.domain.Order
import com.example.taxi.presentation.DeleteWorker
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class OrderItemViewModel @Inject constructor(
    private val context: Application,
    private val getOrderItemUseCase : GetOrderItemUseCase,
    private val loadImageUseCase : LoadImageUseCase,
) : AndroidViewModel(context) {
    fun getShopItem(itemId: Int) = getOrderItemUseCase(itemId)

    fun formatData(dateString : String) : String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        val outFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        val originDate = originalFormat.parse(dateString)
        return originDate?.let{ outFormat.format(it) }.toString()
    }

    fun checkImageOnDevice(imagePath : String) : Boolean{
        val file = File(imagePath)
        return file.exists()
    }

    suspend fun loadImageFromNetwork(imageName : String, dirPath : String) {
        loadImageUseCase.loadImageFromNetwork(imageName, dirPath)
    }
}