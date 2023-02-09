package com.example.appshopping.presentation.main.detail

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.appshopping.R
import com.example.appshopping.base.BaseFragment
import com.example.appshopping.databinding.FragmentDetailBinding
import com.example.appshopping.other.Constant.KEY_PRODUCT_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment :
    BaseFragment<FragmentDetailBinding, DetailViewModel>(R.layout.fragment_detail) {

    private lateinit var imageViewPagerAdapter: ProductImageViewPagerAdapter

    override fun initView() {
        val id = activity?.intent?.getStringExtra(KEY_PRODUCT_ID) ?: ""
        viewModel.onEvent(DetailViewModel.ViewEvent.GetProduct(id))
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.tvProductPrice.text = state.price
                    binding.tvProductDesc.text = state.description
                    binding.tvProductName.text = state.name
                    if(state.imageUrls != null) {
                        imageViewPagerAdapter = ProductImageViewPagerAdapter(state.imageUrls)
                        binding.viewPagerProduct.adapter = imageViewPagerAdapter
                        binding.viewPagerProduct.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        binding.indicator.setViewPager(binding.viewPagerProduct)
                    }
                }
            }
        }
    }

    override fun onViewClicked(view: View) {
        super.onViewClicked(view)
        when(view.id) {
            binding.layoutHeader.iconStart.id -> navigateUpToBelowActivity()
        }
    }

    private fun navigateUpToBelowActivity() {
        activity?.finish()
    }

    override fun initViewListener() {
        binding.layoutHeader.iconStart.setOnClickListener(this@DetailFragment)
    }

    override fun initData() {
    }
}