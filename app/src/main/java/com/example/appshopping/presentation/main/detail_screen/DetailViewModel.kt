package com.example.appshopping.presentation.main.detail_screen

import android.util.Log
import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.domain.usecase.main.add_product.AddProductToCartUseCase
import com.example.appshopping.domain.usecase.main.get_single_product.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
) :
    BaseViewModel<DetailViewModel.ViewState, DetailViewModel.ViewEvent, DetailViewModel.ViewEffect>(
        ViewState()
    ) {

    private var getProductJob: Job? = null
    private var addProductJob: Job? = null

    private fun buyProduct(productModel: ProductModel) {

    }

    private fun getProduct(id: String) {
        getProductJob?.cancel()
        getProductJob = coroutineScope.launch {
            getProductUseCase(id).collect {
                setState(
                    currentState.copy(
                        price = it.price,
                        description = it.description,
                        name = it.name,
                        imageUrls = it.images
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
            is ViewEvent.BuyEvent -> buyProduct(event.productModel)
            is ViewEvent.GetProduct -> getProduct(event.id)
            is ViewEvent.AddProductToCart -> addProductToCart(event.productId)
        }
    }

    data class ViewState(
        val price: String? = null,
        val description: String? = null,
        val name: String? = null,
        val imageUrls: List<String>? = null,
    ) : BaseViewState

    sealed interface ViewEvent : BaseViewEvent {
        data class BuyEvent(val productModel: ProductModel) : ViewEvent
        data class GetProduct(val id: String) : ViewEvent
        data class AddProductToCart(val productId: String) : ViewEvent
    }

    sealed interface ViewEffect : BaseViewEffect {
        object AddProductSuccess: ViewEffect
        data class AddProductFail(val error: String): ViewEffect
    }

}