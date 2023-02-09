package com.example.appshopping.presentation.detail

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.example.appshopping.R
import com.example.appshopping.base.BaseFragment
import com.example.appshopping.databinding.FragmentDetailBinding
import com.example.appshopping.other.Constant.KEY_PRODUCT_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.bumptech.glide.request.target.Target

@AndroidEntryPoint
class DetailFragment :
    BaseFragment<FragmentDetailBinding, DetailViewModel>(R.layout.fragment_detail) {
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
                    Glide.with(requireContext()).load(state.imageUrl)
                        .apply(
                            RequestOptions().fitCenter().format(DecodeFormat.PREFER_ARGB_8888)
                                .override(Target.SIZE_ORIGINAL)
                        )
                        .into(binding.ivProduct)
                }
            }
        }
    }

    override fun initViewListener() {
    }

    override fun initData() {
    }
}