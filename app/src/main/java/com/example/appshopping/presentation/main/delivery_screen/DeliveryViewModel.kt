package com.example.appshopping.presentation.main.delivery_screen

import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState
import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.domain.usecase.main.get_products_by_user_id.GetProductsByUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(private val getProductsByUserIdUseCase: GetProductsByUserIdUseCase) :
    BaseViewModel<DeliveryViewModel.ViewState, DeliveryViewModel.ViewEvent, DeliveryViewModel.ViewEffect>(
        ViewState()
    ) {

    private var getProductsJob: Job? = null

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

    override fun onEvent(event: ViewEvent) {
        when(event) {
            is ViewEvent.ShowProducts -> getProductsByUser(event.listProductId)
        }
    }

    data class ViewState(
        val productList: List<ProductModel> = listOf(),
    ) : BaseViewState

    sealed interface ViewEvent : BaseViewEvent {
        data class ShowProducts(val listProductId: List<String>): ViewEvent
    }

    sealed interface ViewEffect : BaseViewEffect
}