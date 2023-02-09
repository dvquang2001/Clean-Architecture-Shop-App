package com.example.appshopping.presentation.main.product

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.appshopping.R
import com.example.appshopping.base.BaseFragment
import com.example.appshopping.databinding.FragmentAllProductBinding
import com.example.appshopping.databinding.FragmentDeviceProductBinding
import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.other.Constant
import com.example.appshopping.presentation.main.detail.DetailActivity
import com.example.appshopping.presentation.main.home.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentDeviceProduct: BaseFragment<FragmentDeviceProductBinding, ProductViewModel>(R.layout.fragment_device_product) {

    private lateinit var productAdapter: ProductAdapter
    private lateinit var products: List<ProductModel>

    override fun initView() {
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    products = state.list
                    productAdapter = ProductAdapter(products) { product ->
                        val intent = Intent(requireActivity(), DetailActivity::class.java)
                        intent.putExtra(Constant.KEY_PRODUCT_ID,product.id)
                        startActivity(intent)
                    }
                    binding.rcvDeviceProduct.adapter = productAdapter
                }
            }
        }
    }

    override fun initViewListener() {
    }

    override fun initData() {
        viewModel.onEvent(ProductViewModel.ViewEvent.ShowDeviceProduct)
    }
}