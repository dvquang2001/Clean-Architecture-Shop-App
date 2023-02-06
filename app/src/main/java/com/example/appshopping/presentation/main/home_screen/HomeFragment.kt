package com.example.appshopping.presentation.main.home_screen

import android.widget.Toast
import com.example.appshopping.R
import com.example.appshopping.base.BaseFragment
import com.example.appshopping.data.data_source.fakeListProduct
import com.example.appshopping.data.data_source.getListImage
import com.example.appshopping.databinding.FragmentHomeBinding
import com.example.appshopping.presentation.main.adapter.ProductAdapter
import com.example.appshopping.presentation.main.adapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment:
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home){

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var productAdapter: ProductAdapter
    private lateinit var images: List<Int>

    override fun initView() {

    }

    override fun initObserver() {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.state.collect {
//                    binding.tvShow.text = it.list.toString()
//                }
//            }
//        }
    }

    override fun initViewListener() {
    }

    override fun initData() {
       // viewModel.onEvent(HomeViewModel.ViewEvent.ShowList)
        viewPagerAdapter = ViewPagerAdapter(getListImage())
        productAdapter = ProductAdapter(fakeListProduct()) {
            Toast.makeText(requireContext(),it.name,Toast.LENGTH_LONG).show()
        }
        binding.apply {
            viewPager.adapter = viewPagerAdapter
            indicator.setViewPager(viewPager)
            rcvProduct.adapter = productAdapter
        }
    }
}