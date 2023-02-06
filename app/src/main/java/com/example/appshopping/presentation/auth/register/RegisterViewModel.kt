package com.example.appshopping.presentation.auth.register

import android.util.Patterns
import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.usecase.auth.register.RegisterParam
import com.example.appshopping.domain.usecase.auth.register.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<RegisterViewModel.ViewState, RegisterViewModel.ViewEvent, RegisterViewModel.ViewEffect>(
    ViewState()
) {

    private var registerJob: Job? = null

    private fun register(email: String, password: String, confirmedPasswordError: String) {
        val validateRegister = validateRegister(email, password, confirmedPasswordError)
        if (!validateRegister) return
        registerJob?.cancel()
        registerJob = coroutineScope.launch {
            registerUseCase(RegisterParam(email,password)).collect { result ->
                when(result) {
                    is ResultModel.Success -> {
                        setEffect(ViewEffect.RegisterSuccess)
                    }
                    is ResultModel.Error -> {
                        setEffect(
                            ViewEffect.Error(message = result.t.message ?: "Unknown error")
                        )
                    }
                    is ResultModel.Loading -> {
                        setEffect(
                            ViewEffect.Loading
                        )
                    }
                }
            }
        }
    }

    private fun validateRegister(
        email: String,
        password: String,
        confirmedPassword: String
    ): Boolean {
        if(email.trim().isEmpty()) {
            setState(
                currentState.copy(
                    emailError = "Email cannot be empty"
                )
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            setState(
                currentState.copy(
                    emailError = "Invalid email"
                )
            )
            return false
        }
        if (password.trim().isEmpty()) {
            setState(
                currentState.copy(
                    passwordError = "Password cannot be empty"
                )
            )
            return false
        }
        if (password.trim().length < 8) {
            setState(
                currentState.copy(
                    passwordError = "Password must be longer than 8"
                )
            )
            return false
        }
        if (confirmedPassword != password) {
            setState(
                currentState.copy(
                    confirmedPasswordError = "Confirmed password is not the same as password"
                )
            )
            return false
        }
        return true
    }

    override fun onEvent(event: ViewEvent) {
        when(event) {
            is ViewEvent.RegisterEvent -> register(event.email,event.password,event.confirmedPassword)
        }
    }

    data class ViewState(
        val emailError: String? = null,
        val passwordError: String? = null,
        val confirmedPasswordError: String? = null
    ) : BaseViewState

    sealed interface ViewEvent : BaseViewEvent {
        data class RegisterEvent(
            val email: String,
            val password: String,
            val confirmedPassword: String
        ): ViewEvent
    }

    sealed interface ViewEffect : BaseViewEffect {
        data class Error(val message: String) : ViewEffect

        object RegisterSuccess : ViewEffect
        object Loading: ViewEffect
    }

}