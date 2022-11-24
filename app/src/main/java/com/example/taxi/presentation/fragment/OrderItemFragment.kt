package com.example.taxi.presentation.fragment

import android.os.Build
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
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.example.taxi.presentation.DeleteWorker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class OrderItemFragment : Fragment() {

    private lateinit var orderItemViewModel : OrderItemViewModel
    private var shopItemId : Int = UNDEFINED_ID

    private var _binding: FragmentOrderItemBinding? = null
    private val binding: FragmentOrderItemBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentOrderItemBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderItemViewModel = ViewModelProvider(this)[OrderItemViewModel::class.java]
        parseArgv()
    }

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
        val orderItem = orderItemViewModel.getShopItem(shopItemId)
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
        //val cacheDir = IMAGE_DOWNLOAD_PATH_PREFIX
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

        _binding?.ivPhoto?.load(File(photoPath))
        /*_binding?.ivPhoto?.let {
            Glide
                .with(this)
                .load(IMAGE_URL_PREFIX+photo)
                .into(it)
        }*/
        _binding?.tvStartAddress?.text = "${order.startAddress.city} ${order.startAddress.address}"
        _binding?.tvEndAddress?.text = "${order.endAddress.city} ${order.endAddress.address}"
        _binding?.tvOrderTime?.text = date
        _binding?.tvPrice?.text = "${order.price.amount.div(100)} ${order.price.currency}"
        _binding?.tvVehicle?.text = order.vehicle.toString()

    }

    private fun formatData(dateString : String) : String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        val outFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        val originDate = originalFormat.parse(dateString)
        return outFormat.format(originDate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgv() {
        val argv = arguments
        shopItemId = argv?.getInt(ORDER_ITEM_ID, UNDEFINED_ID) ?: UNDEFINED_ID
    }

    companion object {

        private const val ORDER_ITEM_ID = "order_item_id"
        private const val UNDEFINED_ID = -1
        private const val IMAGE_DOWNLOAD_PATH_PREFIX = "/sdcard/Download/"

        fun newIntentOrder(itemId : Int) : OrderItemFragment {
            return OrderItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ORDER_ITEM_ID, itemId)
                }
            }
        }
    }
}