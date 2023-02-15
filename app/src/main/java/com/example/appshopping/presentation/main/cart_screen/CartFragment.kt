package com.example.appshopping.presentation.main.cart_screen

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.appshopping.R
import com.example.appshopping.base.BaseFragment
import com.example.appshopping.databinding.FragmentCartBinding
import com.example.appshopping.databinding.LayoutDialogRemoveBinding
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.other.Constant
import com.example.appshopping.presentation.main.cart_screen.adapter.CartAdapter
import com.example.appshopping.presentation.main.detail_screen.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>(R.layout.fragment_cart) {

    private lateinit var cartAdapter: CartAdapter
    private var userModel: UserModel? = null
    private var totalPrice: Int = 0
    private val payListId = mutableListOf<String>()
    private lateinit var dialogRemoveBinding: LayoutDialogRemoveBinding

    override fun initView() {

    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state.user != null) {
                        userModel = state.user
                    }
                    if (state.productList != null) {
                        if (state.productList.isNotEmpty()) {
                            binding.rcvCart.visibility = View.VISIBLE
                            binding.tvProductsStatus.visibility = View.GONE
                            cartAdapter.submitList(state.productList)
                            binding.rcvCart.adapter = cartAdapter
                        } else {
                            binding.rcvCart.visibility = View.GONE
                            binding.tvProductsStatus.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is CartViewModel.ViewEffect.DeleteSuccess -> {
                            viewModel.onEvent(CartViewModel.ViewEvent.GetUserData)
                            Toast.makeText(
                                requireContext(),
                                "Deleted product from cart",
                                Toast.LENGTH_LONG
                            ).show()
                            viewModel.onEvent(CartViewModel.ViewEvent.ShowProducts(userModel!!.cartProducts))
                        }
                        is CartViewModel.ViewEffect.DeleteFail -> {
                            Toast.makeText(
                                requireContext(),
                                effect.deleteMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        is CartViewModel.ViewEffect.GetUserDataFail -> {
                            Toast.makeText(requireContext(), effect.error, Toast.LENGTH_LONG).show()
                        }
                        is CartViewModel.ViewEffect.GetUserDataSuccess -> {
                            viewModel.onEvent(CartViewModel.ViewEvent.ShowProducts(userModel!!.cartProducts))
                        }

                        is CartViewModel.ViewEffect.PaySuccess -> {
                            binding.tvTotalPrice.text = getString(R.string.total_price, "0")
                            viewModel.onEvent(CartViewModel.ViewEvent.GetUserData)
                            viewModel.onEvent(CartViewModel.ViewEvent.ShowProducts(userModel!!.cartProducts))
                            Toast.makeText(requireContext(), "Pay success", Toast.LENGTH_LONG)
                                .show()
                        }
                        is CartViewModel.ViewEffect.PayFail -> {
                            Toast.makeText(requireContext(), effect.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    override fun onViewClicked(view: View) {
        super.onViewClicked(view)
        when (view.id) {
            binding.layoutHeader.iconStart.id -> navigateUpToBelowActivity()
            binding.btnPay.id -> pay()
        }
    }

    private fun navigateUpToBelowActivity() {
        activity?.finish()
    }

    private fun pay() {
        viewModel.onEvent(
            CartViewModel.ViewEvent.Pay(
                payListId,
                userModel?.accountBalance ?: "0",
                totalPrice.toString()
            )
        )
    }

    override fun initViewListener() {
        binding.layoutHeader.iconStart.setOnClickListener(this@CartFragment)
        binding.btnPay.setOnClickListener(this@CartFragment)
        cartAdapter = CartAdapter(requireContext(),
            onItemCLicked = {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(Constant.KEY_PRODUCT_ID, it)
                startActivity(intent)
            },
            onCheckBoxChecked = { price, id ->
                payListId.add(id)
                totalPrice += price
                binding.tvTotalPrice.text = getString(R.string.total_price, totalPrice.toString())
            },
            onUnCheckBoxChecked = { price, id ->
                if (payListId.contains(id)) {
                    payListId.remove(id)
                }
                totalPrice -= price
                binding.tvTotalPrice.text = getString(R.string.total_price, totalPrice.toString())
            },
            onDeleteClicked = {
                openDeleteDialog(it)
            }
        )
    }

    private fun openDeleteDialog(productId: String) {
        val dialog = Dialog(requireContext())
        dialogRemoveBinding =
            LayoutDialogRemoveBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(dialogRemoveBinding.root)
        }
        val window = dialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes = window?.attributes
        windowAttributes?.gravity = Gravity.CENTER
        window?.attributes = windowAttributes

        dialogRemoveBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialogRemoveBinding.btnConfirmRemove.setOnClickListener {
            viewModel.onEvent(CartViewModel.ViewEvent.DeleteProduct(productId))
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun initData() {
        binding.tvTotalPrice.text = getString(R.string.total_price, "0")
        viewModel.onEvent(CartViewModel.ViewEvent.GetUserData)
    }
}