package com.example.taxi.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.taxi.databinding.FragmentOrderItemBinding
import com.example.taxi.domain.Order
import com.example.taxi.presentation.viewmodel.OrderItemViewModel
import java.io.File
import androidx.lifecycle.*
import androidx.navigation.fragment.navArgs
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.example.taxi.presentation.DeleteWorker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class OrderItemFragment : Fragment() {

    private val orderItemViewModel by lazy {
        ViewModelProvider(this)[OrderItemViewModel::class.java]
    }
    private val args by navArgs<OrderItemFragmentArgs>()

    private var _binding: FragmentOrderItemBinding? = null
    private val binding: FragmentOrderItemBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentOrderItemBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orderItem = orderItemViewModel.getShopItem(args.orderId)
        if (orderItem != null)
        {
            lifecycleScope.launch {
                loadData(orderItem)
            }
        }
        else {
            throw java.lang.RuntimeException("Get nullable Order")
        }
    }

    private suspend fun loadData(order: Order) {
        val photo = order.vehicle.photo
        val cacheDir = "${context?.cacheDir?.toString()}/"
        val photoPath = "$cacheDir$photo"
        val date = formatData(order.orderTime)
        val startAddress = "${order.startAddress.city} ${order.startAddress.address}"
        val endAddress = "${order.endAddress.city} ${order.endAddress.address}"
        val price = "${order.price.amount.div(100)} ${order.price.currency}"
        val driverName = order.vehicle.driverName
        val modelName = order.vehicle.modelName
        val regNumber = order.vehicle.regNumber

        if (!checkImageOnDevice(photoPath)) {
            orderItemViewModel.loadImageFromNetwork(photo, cacheDir)
            val workManager = WorkManager.getInstance(requireContext().applicationContext)
            workManager.enqueue(
                DeleteWorker.makeRequest(photoPath)
            )
        }

        binding.tvStartAddress.text = startAddress
        binding.tvEndAddress.text = endAddress
        binding.tvOrderTime.text = date
        binding.tvPrice.text = price
        binding.tvDriverName.text = driverName
        binding.tvModelName.text = modelName
        binding.tvRegNumber.text = regNumber
        binding.ivPhoto.load(File(photoPath))
    }

    private fun formatData(dateString : String) : String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        val outFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        val originDate = originalFormat.parse(dateString)
        return originDate?.let{ outFormat.format(it) }.toString()
    }

    private fun checkImageOnDevice(imagePath : String) : Boolean{
        val file = File(imagePath)
        return file.exists()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}