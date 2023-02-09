package com.example.appshopping.presentation.main.cart

import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState
import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.domain.usecase.main.get_products.GetProductsUseCase
import com.example.appshopping.domain.usecase.main.get_user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getProductsUseCase: GetProductsUseCase
): BaseViewModel<CartViewModel.ViewState,CartViewModel.ViewEvent,CartViewModel.ViewEffect>(ViewState()) {

    private var payJob: Job? = null
    private var getProductsJob: Job? = null
    private var getUserJob: Job? = null

    private fun payForTheProducts() {
        payJob?.cancel()
        payJob = coroutineScope.launch {
            //TODO
        }
    }

    private fun getProductsByUser(listProductId: List<String>) {
        getProductsJob?.cancel()
        getProductsJob = coroutineScope.launch {
            getProductsUseCase().collect {
                setState(
                    currentState.copy(
                        productList = it.filter { product -> product.id == listProductId.forEach { product.id = it }}
                    )
                )
            }
        }
    }

    private fun getUserInfo() {
        getUserJob?.cancel()
        getUserJob = coroutineScope.launch {
            getUserInfoUseCase().collect {
                setState(
                    currentState.copy(
                        userModel = it
                    )
                )
            }
        }
    }

    private fun validatePayment() {
        //TODO
    }

    override fun onEvent(event: ViewEvent) {
        when(event) {
            is ViewEvent.Pay -> payForTheProducts()
            is ViewEvent.ShowProducts -> getProductsByUser(event.listProductId)
            is ViewEvent.GetUserInfo -> getUserInfo()
        }
    }

    data class ViewState(
        val userModel: UserModel? = null,
        val productList: List<ProductModel>? = null
    ) : BaseViewState

    sealed interface ViewEvent: BaseViewEvent {
        object Pay: ViewEvent
        object GetUserInfo: ViewEvent
        data class ShowProducts(val listProductId: List<String>): ViewEvent
    }

    sealed interface ViewEffect: BaseViewEffect {

        data class Error(val message: String): ViewEffect
        object PaySuccess: ViewEffect
    }
}