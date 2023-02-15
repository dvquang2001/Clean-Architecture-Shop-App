package com.example.appshopping.presentation.auth.init_user

import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.usecase.auth.init_user.InitUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitDataViewModel @Inject constructor(
    private val initUserUseCase: InitUserUseCase,
) : BaseViewModel<InitDataViewModel.ViewState, InitDataViewModel.ViewEvent, InitDataViewModel.ViewEffect>(
    ViewState
) {

    private var initUserJob: Job? = null

    private fun initUser() {
        initUserJob?.cancel()
        initUserJob = coroutineScope.launch {
            initUserUseCase().collect { result ->
                when(result) {
                    is ResultModel.Success -> setEffect(ViewEffect.GetInitUserSuccess)
                    is ResultModel.Loading -> setEffect(ViewEffect.Loading)
                    is ResultModel.Error -> setEffect(ViewEffect.GetInitUserFail(result.t.message ?: "Unknown error"))
                }
            }
        }
    }

    override fun onEvent(event: ViewEvent) {
        when(event) {
            is ViewEvent.InitUserData -> initUser()
        }
    }

    object ViewState : BaseViewState

    sealed interface ViewEvent : BaseViewEvent {
        object InitUserData: ViewEvent
    }

    sealed interface ViewEffect : BaseViewEffect {
        object GetInitUserSuccess: ViewEffect
        data class GetInitUserFail(val error: String): ViewEffect
        object Loading: ViewEffect
    }
}