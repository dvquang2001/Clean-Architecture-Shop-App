package com.example.appshopping.presentation.auth.reset_password

import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState

class CheckEmailViewModel:
    BaseViewModel<CheckEmailViewModel.ViewState,CheckEmailViewModel.ViewEvent,CheckEmailViewModel.ViewEffect>(ViewState()){

    override fun onEvent(event: ViewEvent) {
    }

    class ViewState: BaseViewState

    sealed interface ViewEvent: BaseViewEvent

    sealed interface ViewEffect: BaseViewEffect

}