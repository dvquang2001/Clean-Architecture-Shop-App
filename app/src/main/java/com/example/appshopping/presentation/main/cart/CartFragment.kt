package com.example.appshopping.presentation.main.cart

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.appshopping.R
import com.example.appshopping.base.BaseFragment
import com.example.appshopping.databinding.FragmentCartBinding
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.other.Constant
import com.example.appshopping.presentation.main.cart.adapter.CartAdapter
import com.example.appshopping.presentation.main.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>(R.layout.fragment_cart) {

    private lateinit var cartAdapter: CartAdapter
    private lateinit var userModel: UserModel

    override fun initView() {

    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    userModel = state.userModel!!
                    viewModel.onEvent(CartViewModel.ViewEvent.ShowProducts(userModel.productId))
                    if (state.productList != null)
                        cartAdapter.submitList(state.productList)
                }
            }
        }
    }

    override fun onViewClicked(view: View) {
        super.onViewClicked(view)
        when (view.id) {
            binding.layoutHeader.iconStart.id -> navigateUpToBelowActivity()
        }
    }

    private fun navigateUpToBelowActivity() {
        activity?.finish()
    }

    override fun initViewListener() {
        binding.layoutHeader.iconStart.setOnClickListener(this@CartFragment)
        cartAdapter = CartAdapter(
            onItemCLicked = {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(Constant.KEY_PRODUCT_ID, it)
                startActivity(intent)
            },
            onCheckBoxChecked = {
                //todo
            }
        )
    }

    override fun initData() {
        viewModel.onEvent(CartViewModel.ViewEvent.GetUserInfo)
    }
}