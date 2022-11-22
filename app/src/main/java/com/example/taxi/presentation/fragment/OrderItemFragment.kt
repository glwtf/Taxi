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
        val photoPath = IMAGE_DOWNLOAD_PATH_PREFIX+photo
        val date = formatData(order.orderTime)

        if (!orderItemViewModel.checkImageOnDevice(photoPath)) {
            orderItemViewModel.loadImageFromNetwork(photo)
        }
        _binding?.ivPhoto?.load(File(photoPath))

        _binding?.tvStartAddress?.text = "${order.startAddress.city} ${order.startAddress.address}"
        _binding?.tvEndAddress?.text = "${order.endAddress.city} ${order.endAddress.address}"
        _binding?.tvOrderTime?.text = order.orderTime
        _binding?.tvPrice?.text = "${order.price.amount.div(100)} ${order.price.currency}"
        _binding?.tvVehicle?.text = order.vehicle.toString()

    }

    private fun formatData(dateString : String) : Date {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'HH:MM");
        return sdf.parse(dateString)
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