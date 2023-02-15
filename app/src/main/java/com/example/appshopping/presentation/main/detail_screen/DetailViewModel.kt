package com.example.appshopping.presentation.main.detail_screen

import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.domain.usecase.main.add_product.AddProductToCartUseCase
import com.example.appshopping.domain.usecase.main.buy.BuyUseCase
import com.example.appshopping.domain.usecase.main.get_single_product.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val buyUseCase: BuyUseCase
) :
    BaseViewModel<DetailViewModel.ViewState, DetailViewModel.ViewEvent, DetailViewModel.ViewEffect>(
        ViewState()
    ) {

    private var getProductJob: Job? = null
    private var addProductJob: Job? = null
    private var buyJob: Job? = null



    private fun buyProduct(cartProductId: String,productPrice: String) {
        buyJob?.cancel()
        buyJob = coroutineScope.launch {
            buyUseCase(cartProductId,productPrice).collect { result ->
                when(result) {
                    is ResultModel.Success -> {
                        setEffect(ViewEffect.BuySuccess)
                    }
                    is ResultModel.Error -> {
                        setEffect(ViewEffect.BuyFail(result.t.message ?: "Unknown error"))
                    }
                }
            }
        }
    }

    private fun getProduct(id: String) {
        getProductJob?.cancel()
        getProductJob = coroutineScope.launch {
            getProductUseCase(id).collect {
                setState(
                    currentState.copy(
                        productModel = it
                    )
                )
            }
        }
    }

    private fun addProductToCart(productId: String) {
        addProductJob?.cancel()
        addProductJob = coroutineScope.launch {
            addProductToCartUseCase(productId).collect { result ->
                when(result) {
                    is ResultModel.Success -> {
                        setEffect(
                            ViewEffect.AddProductSuccess
                        )

                    }
                    is ResultModel.Error -> {
                        setEffect(
                            ViewEffect.AddProductFail(result.t.message ?: "Unknown error")
                        )
                    }
                }
            }
        }
    }

    override fun onEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.BuyEvent -> buyProduct(event.cartProductId,event.productPrice)
            is ViewEvent.GetProduct -> getProduct(event.id)
            is ViewEvent.AddProductToCart -> addProductToCart(event.productId)
        }
    }

    data class ViewState(
        val productModel: ProductModel? = null,
    ) : BaseViewState

    sealed interface ViewEvent : BaseViewEvent {
        data class BuyEvent(val cartProductId: String,val productPrice: String) : ViewEvent
        data class GetProduct(val id: String) : ViewEvent
        data class AddProductToCart(val productId: String) : ViewEvent
    }

    sealed interface ViewEffect : BaseViewEffect {
        object AddProductSuccess: ViewEffect
        data class AddProductFail(val error: String): ViewEffect
        object BuySuccess: ViewEffect
        data class BuyFail(val error: String): ViewEffect
    }

}