package com.example.appshopping.presentation.main.detail_screen

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.appshopping.R
import com.example.appshopping.base.BaseFragment
import com.example.appshopping.databinding.FragmentDetailBinding
import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.other.Constant.KEY_PRODUCT_ID
import com.example.appshopping.presentation.main.detail_screen.adapter.ProductImageViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment :
    BaseFragment<FragmentDetailBinding, DetailViewModel>(R.layout.fragment_detail) {

    private lateinit var imageViewPagerAdapter: ProductImageViewPagerAdapter
    private var id: String? = null
    private var product: ProductModel? = null

    override fun initView() {
        id = activity?.intent?.getStringExtra(KEY_PRODUCT_ID) ?: ""
        viewModel.onEvent(DetailViewModel.ViewEvent.GetProduct(id!!))
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    product = state.productModel
                    binding.tvProductPrice.text = state.productModel?.price
                    binding.tvProductDesc.text = state.productModel?.description
                    binding.tvProductName.text = state.productModel?.name
                    if(state.productModel?.images != null) {
                        imageViewPagerAdapter = ProductImageViewPagerAdapter(state.productModel.images)
                        binding.viewPagerProduct.adapter = imageViewPagerAdapter
                        binding.viewPagerProduct.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        binding.indicator.setViewPager(binding.viewPagerProduct)
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.effect.collect { effect ->
                when(effect) {
                    is DetailViewModel.ViewEffect.AddProductSuccess -> {
                        Toast.makeText(requireContext(),"Add product to cart success",Toast.LENGTH_LONG).show()
                    }
                    is DetailViewModel.ViewEffect.AddProductFail -> {
                        Toast.makeText(requireContext(),effect.error,Toast.LENGTH_LONG).show()
                    }
                    is DetailViewModel.ViewEffect.BuySuccess -> {
                        Toast.makeText(requireContext(),"Buy success, your item is being confirmed",Toast.LENGTH_LONG).show()
                    }
                    is DetailViewModel.ViewEffect.BuyFail -> {
                        Toast.makeText(requireContext(),effect.error,Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onViewClicked(view: View) {
        super.onViewClicked(view)
        when(view.id) {
            binding.layoutHeader.iconStart.id -> navigateUpToBelowActivity()
            binding.btnAddToCart.id -> addProductToCart(id!!)
            binding.btnBuy.id -> buyProduct()
        }
    }

    private fun navigateUpToBelowActivity() {
        activity?.finish()
    }

    private fun addProductToCart(productId: String) {
        viewModel.onEvent(DetailViewModel.ViewEvent.AddProductToCart(productId))
    }

    private fun buyProduct() {
        if(product != null)
            viewModel.onEvent(DetailViewModel.ViewEvent.BuyEvent(product?.id!!,product?.price!!))
    }

    override fun initViewListener() {
        binding.apply {
            layoutHeader.iconStart.setOnClickListener(this@DetailFragment)
            btnAddToCart.setOnClickListener(this@DetailFragment)
            btnBuy.setOnClickListener(this@DetailFragment)
        }
    }

    override fun initData() {
    }
}