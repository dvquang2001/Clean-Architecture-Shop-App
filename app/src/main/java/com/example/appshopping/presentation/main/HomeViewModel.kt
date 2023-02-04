package com.example.appshopping.presentation.main

import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState
import com.example.appshopping.domain.usecase.getUserData.GetUserDataUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val getUserDataUseCase: GetUserDataUseCase) :
    BaseViewModel<HomeViewModel.ViewState, HomeViewModel.ViewEvent, HomeViewModel.ViewEffect>(
        ViewState()
    ) {

    override fun onEvent(event: ViewEvent) {

    }

    data class ViewState(
        val error: String? = null
    ) : BaseViewState

    sealed interface ViewEvent : BaseViewEvent

    sealed interface ViewEffect : BaseViewEffect
}