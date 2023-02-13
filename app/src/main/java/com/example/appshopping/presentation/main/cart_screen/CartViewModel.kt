package com.example.appshopping.presentation.main.cart_screen

import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.domain.usecase.main.delete_product_from_cart.DeleteProductUseCase
import com.example.appshopping.domain.usecase.main.get_products_by_user_id.GetProductsByUserIdUseCase
import com.example.appshopping.domain.usecase.pay.PayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getProductsByUserIdUseCase: GetProductsByUserIdUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val payUseCase: PayUseCase
): BaseViewModel<CartViewModel.ViewState,CartViewModel.ViewEvent,CartViewModel.ViewEffect>(ViewState()) {

    private var getProductsJob: Job? = null
    private var deleteProductJob: Job? = null
    private var payJob: Job? = null

    private fun getProductsByUser(listProductId: List<String>) {
        getProductsJob?.cancel()
        getProductsJob = coroutineScope.launch {
            getProductsByUserIdUseCase(listProductId).collectLatest {
                setState(
                    currentState.copy(
                        productList = it
                    )
                )
            }
        }
    }

    private fun deleteProduct(productId: String) {
        deleteProductJob?.cancel()
        deleteProductJob = coroutineScope.launch {
            deleteProductUseCase(productId).collect { result ->
                when(result) {
                    is ResultModel.Success -> {
                        setEffect(ViewEffect.DeleteSuccess)
                    }
                    is ResultModel.Error -> {
                        setEffect(ViewEffect.DeleteFail(result.t.message ?: "Delete fail"))
                    }
                }
            }
        }
    }

    private fun validatePayment(userAccountBalance: String, totalPrice: String): Boolean {
        if(userAccountBalance.toLong() < totalPrice.toLong()) {
            setEffect(ViewEffect.PayFail("Your account is not enough to perform the service"))
            return false
        }
        return true
    }

    private fun pay(cartProductIds: List<String>,userAccountBalance: String, totalPrice: String) {
        payJob?.cancel()
        if(!validatePayment(userAccountBalance,totalPrice)) return
        val newUserAccountBalance = userAccountBalance.toLong() - totalPrice.toLong()
        payJob = coroutineScope.launch {
            payUseCase(cartProductIds,newUserAccountBalance.toString()).collect { result ->
                when (result) {
                    is ResultModel.Success -> ViewEffect.PaySuccess
                    is ResultModel.Error -> ViewEffect.PayFail(result.t.message ?: "Unknown error")
                }
            }
        }
    }

    override fun onEvent(event: ViewEvent) {
        when(event) {
            is ViewEvent.ShowProducts -> getProductsByUser(event.listProductId)
            is ViewEvent.DeleteProduct -> deleteProduct(event.productId)
            is ViewEvent.Pay -> pay(event.cartProductIds,event.userAccountBalance,event.totalPrice)
        }
    }

    data class ViewState(
        val productList: List<ProductModel>? = null
    ) : BaseViewState

    sealed interface ViewEvent: BaseViewEvent {
        data class ShowProducts(val listProductId: List<String>): ViewEvent
        data class DeleteProduct(val productId: String): ViewEvent
        data class Pay(val cartProductIds: List<String>,val userAccountBalance: String,val totalPrice: String): ViewEvent
    }

    sealed interface ViewEffect: BaseViewEffect {
        object DeleteSuccess: ViewEffect
        data class DeleteFail(val deleteMessage: String): ViewEffect
        object PaySuccess: ViewEffect
        data class PayFail(val message: String): ViewEffect
    }
}