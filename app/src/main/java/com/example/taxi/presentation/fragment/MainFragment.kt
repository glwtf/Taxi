package com.example.taxi.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.taxi.R
import com.example.taxi.databinding.FragmentMainBinding
import com.example.taxi.presentation.viewmodel.MainFragmentViewModel
import com.example.taxi.presentation.recyclerview.OrderListAdapter

class MainFragment : Fragment() {

    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var rvAdapter: OrderListAdapter

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentMainBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        viewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]
        viewModel.ldOrders.observe(viewLifecycleOwner){ item ->
            rvAdapter.submitList(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRecyclerView() {
        val rvShopList = _binding!!.rvOrderList
        with(rvShopList) {
            rvAdapter = OrderListAdapter()
            adapter = rvAdapter
        }
        setupClickListener()
    }

    private fun setupClickListener() {
        rvAdapter.onShopItemClickListener = { orderItem ->
                launchFragment(orderItem.id)
            }
        }

    private fun launchFragment(orderId : Int) {
        requireActivity().supportFragmentManager.popBackStack(
            MAIN_FRAGMENT_NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, OrderItemFragment.newIntentOrder(orderId))
            .addToBackStack(MAIN_FRAGMENT_NAME)
            .commit()
    }

    companion object {
        const val MAIN_FRAGMENT_NAME = "main_fragment"
        fun newMainFragmentInstance() = MainFragment()
    }

}

