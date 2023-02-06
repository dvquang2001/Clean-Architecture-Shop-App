package com.example.appshopping.presentation.auth.reset_password

import android.util.Patterns
import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState
import com.example.appshopping.domain.usecase.auth.reset_password.ResetPasswordParam
import com.example.appshopping.domain.usecase.auth.reset_password.ResetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : BaseViewModel<ResetPasswordViewModel.ViewState, ResetPasswordViewModel.ViewEvent, ResetPasswordViewModel.ViewEffect>
    (ViewState()) {

    private var sendJob: Job? = null

    private fun send(email: String) {
        val validateEmail = validateEmail(email)
        if(!validateEmail) return
        sendJob?.cancel()
        sendJob = coroutineScope.launch {
            resetPasswordUseCase(ResetPasswordParam(email))
            setEffect(ViewEffect.SendComplete)
        }
    }

    private fun validateEmail(email: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            setState(
                currentState.copy(
                    emailError = "Invalid email"
                )
            )
            return false
        }
        return true
    }

    override fun onEvent(event: ViewEvent) {
        when(event) {
            is ViewEvent.SendEvent -> send(event.email)
        }
    }

    data class ViewState(
        val emailError: String? = null
    ) : BaseViewState

    sealed interface ViewEvent : BaseViewEvent {
        data class SendEvent(
            val email: String
        ) : ViewEvent
    }

    sealed interface ViewEffect : BaseViewEffect {
        object SendComplete: ViewEffect
    }

}