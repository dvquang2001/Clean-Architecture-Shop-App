package com.example.appshopping.presentation.auth.login

import android.util.Patterns
import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.usecase.auth.check_login.CheckLoginUseCase
import com.example.appshopping.domain.usecase.auth.login.LoginParam
import com.example.appshopping.domain.usecase.auth.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val checkLoginUseCase: CheckLoginUseCase
) : BaseViewModel<LoginViewModel.ViewState, LoginViewModel.ViewEvent, LoginViewModel.ViewEffect>(
    ViewState()
) {

    private var loginJob: Job? = null

    private fun login(email: String, password: String) {
        val validateLogin = validateLogin(email, password)
        if (!validateLogin) return
        loginJob?.cancel()
        loginJob = coroutineScope.launch {
            loginUseCase(LoginParam(email, password)).collect { result ->
                when (result) {
                    is ResultModel.Success -> {
                        setEffect(ViewEffect.LoginSuccess)
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


    private fun validateLogin(email: String, password: String): Boolean {
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
        return true
    }

    private fun checkLogin() {
        val loggedIn = checkLoginUseCase()
        setState(
            currentState.copy(
                loggedIn = loggedIn
            )
        )
    }

    override fun onEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.LoginEvent -> login(event.email, event.password)
            is ViewEvent.CheckLogin -> checkLogin()
        }
    }

    data class ViewState(
        val loggedIn: Boolean = false,
        val emailError: String? = null,
        val passwordError: String? = null
    ) : BaseViewState

    sealed interface ViewEvent : BaseViewEvent {
        data class LoginEvent(
            val email: String,
            val password: String
        ) : ViewEvent

        object CheckLogin : ViewEvent
    }

    sealed interface ViewEffect : BaseViewEffect {
        data class Error(val message: String) : ViewEffect

        object LoginSuccess : ViewEffect
        object Loading : ViewEffect
    }


}