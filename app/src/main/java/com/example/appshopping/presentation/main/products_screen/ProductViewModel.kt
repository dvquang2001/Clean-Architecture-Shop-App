package com.example.appshopping.presentation.main.products_screen

import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState
import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.domain.usecase.main.get_products.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val getProductsUseCase: GetProductsUseCase) :
    BaseViewModel<ProductViewModel.ViewState, ProductViewModel.ViewEvent, ProductViewModel.ViewEffect>(
        ViewState()
    ) {

    private var getProductsJob: Job? = null

    private fun getAllProducts() {
        getProductsJob?.cancel()
        getProductsJob = coroutineScope.launch {
            getProductsUseCase().collect {
                setState(
                    currentState.copy(
                        list = it
                    )
                )
            }
        }
    }

    private fun getDeviceProducts() {
        getProductsJob?.cancel()
        getProductsJob = coroutineScope.launch {
            getProductsUseCase().collect {
                setState(
                    currentState.copy(
                        list = it.filter { product -> product.type == "Device" }
                    )
                )
            }
        }
    }

    private fun getFashionProducts() {
        getProductsJob?.cancel()
        getProductsJob = coroutineScope.launch {
            getProductsUseCase().collect {
                setState(
                    currentState.copy(
                        list = it.filter { product -> product.type == "Fashion" }
                    )
                )
            }
        }
    }


    override fun onEvent(event: ViewEvent) {
        when(event) {
            is ViewEvent.ShowAllProduct -> getAllProducts()
            is ViewEvent.ShowDeviceProduct -> getDeviceProducts()
            is ViewEvent.ShowFashionProduct -> getFashionProducts()
        }
    }

    data class ViewState(
        val list: List<ProductModel> = listOf()
    ) : BaseViewState

    sealed interface ViewEvent: BaseViewEvent {
        object ShowAllProduct: ViewEvent
        object ShowDeviceProduct: ViewEvent
        object ShowFashionProduct: ViewEvent
    }

    sealed interface ViewEffect : BaseViewEffect
}