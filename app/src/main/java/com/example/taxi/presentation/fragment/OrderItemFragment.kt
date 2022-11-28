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

        if (!orderItemViewModel.checkImageOnDevice(photoPath)) {
            orderItemViewModel.loadImageFromNetwork(photo, cacheDir)
            Log.d("SERVICE_TAG", "Load file $photoPath")

            val workManager = WorkManager.getInstance(requireContext().applicationContext)
            workManager.enqueue(
                DeleteWorker.makeRequest(photoPath)
            )
        }

        binding.tvStartAddress.text = "${order.startAddress.city} ${order.startAddress.address}"
        binding.tvEndAddress.text = "${order.endAddress.city} ${order.endAddress.address}"
        binding.tvOrderTime.text = date
        binding.tvPrice.text = "${order.price.amount.div(100)} ${order.price.currency}"
        binding.tvDriverName.text = order.vehicle.driverName
        binding.tvModelName.text = order.vehicle.modelName
        binding.tvRegNumber.text = order.vehicle.regNumber
        binding.ivPhoto.load(File(photoPath))
        /*Glide.with(this)
            .load(photoPath)
                .into(binding.ivPhoto)*/

    }

    private fun formatData(dateString : String) : String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        val outFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        val originDate = originalFormat.parse(dateString)
        return outFormat.format(originDate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}