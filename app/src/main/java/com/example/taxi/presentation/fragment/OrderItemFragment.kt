package com.example.taxi.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.taxi.databinding.FragmentMainBinding
import com.example.taxi.databinding.FragmentOrderItemBinding
import com.example.taxi.presentation.viewmodel.OrderItemViewModel

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
        _binding?.tvStartAddress?.text = "${orderItem?.startAddress?.city} ${orderItem?.startAddress?.address}"
        _binding?.tvEndAddress?.text = "${orderItem?.endAddress?.city} ${orderItem?.endAddress?.address}"
        _binding?.tvOrderTime?.text = orderItem?.orderTime
        _binding?.tvPrice?.text = "${orderItem?.price?.amount?.div(100)} ${orderItem?.price?.amount}"
        _binding?.tvVehicle?.text = orderItem?.vehicle.toString()
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

        fun newIntentOrder(itemId : Int) : OrderItemFragment {
            return OrderItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ORDER_ITEM_ID, itemId)
                }
            }
        }
    }
}