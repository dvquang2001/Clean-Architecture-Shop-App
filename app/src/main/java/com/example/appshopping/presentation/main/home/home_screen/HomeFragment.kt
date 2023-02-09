package com.example.appshopping.presentation.main.home.home_screen

import android.content.Intent
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.appshopping.R
import com.example.appshopping.base.BaseFragment
import com.example.appshopping.data.data_source.getListImage
import com.example.appshopping.databinding.FragmentHomeBinding
import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.other.Constant.KEY_PRODUCT_ID
import com.example.appshopping.presentation.main.cart.CartActivity
import com.example.appshopping.presentation.main.detail.DetailActivity
import com.example.appshopping.presentation.main.home.adapter.ProductAdapter
import com.example.appshopping.presentation.main.product.ProductActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    private lateinit var productAdapter: ProductAdapter
    private lateinit var products: List<ProductModel>


    override fun initView() {
        val listImage = mutableListOf<SlideModel>()
        for(image in getListImage()) {
            listImage.add(SlideModel(image))
        }
        binding.imageSlider.setImageList(listImage,ScaleTypes.CENTER_CROP)
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    products = state.list
                    productAdapter = ProductAdapter(products) { product ->
                        val intent = Intent(requireActivity(), DetailActivity::class.java)
                        intent.putExtra(KEY_PRODUCT_ID,product.id)
                        startActivity(intent)
                    }
                    binding.rcvProduct.adapter = productAdapter
                }
            }
        }
    }

    override fun onViewClicked(view: View) {
        super.onViewClicked(view)
        when(view.id) {
            binding.tvNavigateToProductActivity.id -> navigateToProductActivity()
            binding.layoutHeader.iconEnd.id -> navigateToCartActivity()
        }
    }

    private fun navigateToProductActivity() {
        startActivity(Intent(requireActivity(), ProductActivity::class.java))
    }

    private fun navigateToCartActivity() {
        startActivity(Intent(requireActivity(),CartActivity::class.java))
    }

    override fun initViewListener() {
        binding.tvNavigateToProductActivity.setOnClickListener(this@HomeFragment)
        binding.layoutHeader.iconEnd.setOnClickListener(this@HomeFragment)
    }

    override fun initData() {
        viewModel.onEvent(HomeViewModel.ViewEvent.ShowList)
    }
}