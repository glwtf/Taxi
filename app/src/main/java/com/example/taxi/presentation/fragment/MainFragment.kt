package com.example.taxi.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.taxi.R
import com.example.taxi.databinding.FragmentMainBinding
import com.example.taxi.presentation.viewmodel.MainFragmentViewModel
import com.example.taxi.presentation.recyclerview.OrderListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainFragmentViewModel by viewModels()

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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.ldOrders.collect{ item ->
                    rvAdapter.submitList(item)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRecyclerView() {
        val rvShopList = binding.rvOrderList
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
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToOrderItemFragment(orderId)
        )
    }

}

