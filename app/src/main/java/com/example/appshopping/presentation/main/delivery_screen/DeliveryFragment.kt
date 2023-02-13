package com.example.appshopping.presentation.main.delivery_screen

import android.content.Intent
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.appshopping.R
import com.example.appshopping.base.BaseFragment
import com.example.appshopping.databinding.FragmentDeliveryBinding
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.other.Constant
import com.example.appshopping.presentation.main.confirmation_screen.adapter.PurchasedCartAdapter
import com.example.appshopping.presentation.main.detail_screen.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeliveryFragment: BaseFragment<FragmentDeliveryBinding,DeliveryViewModel>(R.layout.fragment_delivery) {

    lateinit var purchasedCartAdapter: PurchasedCartAdapter
    private var userModel: UserModel? = null

    override fun initView() {

    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state.productList.isNotEmpty()) {
                        binding.tvProductsStatus.visibility = View.GONE
                        purchasedCartAdapter.submitList(state.productList)
                        binding.rcvDeliveryCart.adapter = purchasedCartAdapter
                    } else {
                        binding.tvProductsStatus.visibility = View.VISIBLE
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

    override fun initViewListener() {
        binding.apply {
            tvProductsStatus.setOnClickListener(this@DeliveryFragment)
            binding.layoutHeader.iconStart.setOnClickListener(this@DeliveryFragment)
        }
        purchasedCartAdapter = PurchasedCartAdapter(requireContext(),
            getString(R.string.delivery_desc),
            onItemCLicked = {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(Constant.KEY_PRODUCT_ID, it)
                startActivity(intent)
            }
        )
    }

    private fun navigateUpToBelowActivity() {
        activity?.finish()
    }

    override fun initData() {
        userModel = activity?.intent?.getSerializableExtra(Constant.USER_DATA_INFO) as UserModel
        if (userModel != null) {
            viewModel.onEvent(DeliveryViewModel.ViewEvent.ShowProducts(userModel!!.deliveryProducts))
        }
    }
}