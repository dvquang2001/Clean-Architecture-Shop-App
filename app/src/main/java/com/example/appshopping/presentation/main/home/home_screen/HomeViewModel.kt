package com.example.appshopping.presentation.main.home.home_screen

import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState
import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.domain.usecase.main.get_products.GetProductsUseCase
import com.example.appshopping.domain.usecase.main.get_user.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserDataUseCase,
    private val getProductsUseCase: GetProductsUseCase,
) :
    BaseViewModel<HomeViewModel.ViewState, HomeViewModel.ViewEvent, HomeViewModel.ViewEffect>(
        ViewState()
    ) {

    private var getProductsJob: Job? = null
    private var getUserInfoJob: Job? = null
    private fun getAllProducts() {
        getProductsJob?.cancel()
        getProductsJob = coroutineScope.launch {
            getProductsUseCase().collect {
                setState(
                    currentState.copy(
                        list = it.take(6)
                    )
                )
            }
        }
    }


    override fun onEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.ShowList -> getAllProducts()
        }
    }

    data class ViewState(
        val list: List<ProductModel> = listOf(),
        val userModel: UserModel? = null
    ) : BaseViewState

    sealed interface ViewEvent : BaseViewEvent {
        object ShowList : ViewEvent
    }

    sealed interface ViewEffect : BaseViewEffect
}