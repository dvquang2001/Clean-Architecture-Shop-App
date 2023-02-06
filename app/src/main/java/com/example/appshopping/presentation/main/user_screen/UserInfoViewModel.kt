package com.example.appshopping.presentation.main.user_screen

import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState
import com.example.appshopping.domain.model.auth.LoginModel
import com.example.appshopping.domain.usecase.auth.getUserData.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(private val getUserDataUseCase: GetUserDataUseCase) :
    BaseViewModel<UserInfoViewModel.ViewState, UserInfoViewModel.ViewEvent, UserInfoViewModel.ViewEffect>(
        ViewState()
    ) {

    private var getUserJob: Job? = null

    private fun getUserData() {
        getUserJob?.cancel()
        getUserJob = coroutineScope.launch {
            getUserDataUseCase().collect { result ->
               if(result != null) {
                   setState(
                       currentState.copy(
                           currentUser = result
                       )
                   )
               }
            }
        }
    }

    override fun onEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.CheckUserData -> getUserData()
        }
    }

    data class ViewState(
        val currentUser: LoginModel? = null,
    ) : BaseViewState

    sealed interface ViewEvent : BaseViewEvent {
        object CheckUserData : ViewEvent
    }

    sealed interface ViewEffect : BaseViewEffect {
        object GetUserSuccess : ViewEffect

        data class GetUserFail(val message: String) : ViewEffect
    }

}