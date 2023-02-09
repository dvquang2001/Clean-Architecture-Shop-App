package com.example.appshopping.presentation.main.setting_screen

import com.example.appshopping.base.*

class SettingViewModel
    : BaseViewModel<SettingViewModel.ViewState,SettingViewModel.ViewEvent,SettingViewModel.ViewEffect>(ViewState) {

    override fun onEvent(event: ViewEvent) {
        TODO("Not yet implemented")
    }

    object ViewState: BaseViewState

    sealed interface ViewEvent: BaseViewEvent

    sealed interface ViewEffect: BaseViewEffect
}