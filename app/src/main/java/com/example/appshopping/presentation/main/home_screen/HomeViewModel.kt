package com.example.appshopping.presentation.main.home_screen

import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState
import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.domain.usecase.auth.getUserData.GetUserDataUseCase
import com.example.appshopping.domain.usecase.main.get_product.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getProductUseCase: GetProductUseCase,
) :
    BaseViewModel<HomeViewModel.ViewState, HomeViewModel.ViewEvent, HomeViewModel.ViewEffect>(
        ViewState()
    ) {

    private var getProductsJob: Job? = null

    private fun getAllProducts() {
        getProductsJob?.cancel()
        getProductsJob = coroutineScope.launch {
            getProductUseCase().collect {
                setState(
                    currentState.copy(
                        list = it
                    )
                )
            }
        }
    }

    override fun onEvent(event: ViewEvent) {
        when(event) {
            is ViewEvent.ShowList -> getAllProducts()
        }
    }

    data class ViewState(
        val error: String? = null,
        val list: List<ProductModel> = listOf(),
    ) : BaseViewState

    sealed interface ViewEvent : BaseViewEvent {
        object ShowList : ViewEvent
    }

    sealed interface ViewEffect : BaseViewEffect
}