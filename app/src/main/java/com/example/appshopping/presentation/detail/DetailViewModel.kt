package com.example.appshopping.presentation.detail

import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState
import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.domain.usecase.main.get_single_product.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getProductUseCase: GetProductUseCase) :
    BaseViewModel<DetailViewModel.ViewState,DetailViewModel.ViewEvent,DetailViewModel.ViewEffect>(ViewState()){

    private var getProductJob: Job? = null

    private fun buyProduct(productModel: ProductModel) {

    }

    private fun getProduct(id: String) {
        getProductJob?.cancel()
        getProductJob = coroutineScope.launch {
            getProductUseCase(id).collect {
                setState(currentState.copy(
                    price = it.price,
                    description = it.description,
                    name = it.name,
                    imageUrl = it.image
                ))
            }
        }
    }

    override fun onEvent(event: ViewEvent) {
        when(event) {
            is ViewEvent.BuyEvent -> buyProduct(event.productModel)
            is ViewEvent.GetProduct -> getProduct(event.id)
        }
    }

    data class ViewState(
        val price: String? = null,
        val description: String? = null,
        val name: String? = null,
        val imageUrl: String? = null
    ) : BaseViewState

    sealed interface ViewEvent : BaseViewEvent {
        data class BuyEvent(val productModel: ProductModel): ViewEvent
        data class GetProduct(val id: String): ViewEvent
    }

    sealed interface ViewEffect: BaseViewEffect

}