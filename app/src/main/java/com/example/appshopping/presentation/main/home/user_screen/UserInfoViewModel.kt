package com.example.appshopping.presentation.main.home.user_screen

import android.util.Log
import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.domain.usecase.auth.logout.LogoutUseCase
import com.example.appshopping.domain.usecase.main.change_user_data.ChangeUserDataUseCase
import com.example.appshopping.domain.usecase.main.get_user.GetUserDataUseCase
import com.example.appshopping.domain.usecase.auth.login.LoginParam
import com.example.appshopping.domain.usecase.main.change_password.ChangePasswordUseCase
import com.example.appshopping.domain.usecase.main.get_user.UserParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val changeUserDataUseCase: ChangeUserDataUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val logoutUseCase: LogoutUseCase,
) :
    BaseViewModel<UserInfoViewModel.ViewState, UserInfoViewModel.ViewEvent, UserInfoViewModel.ViewEffect>(
        ViewState()
    ) {

    private var getUserInfoJob: Job? = null
    private var changeUserJob: Job? = null
    private var changePasswordJob: Job? = null
    private var logoutJob: Job? = null

    private fun getUser() {
        getUserInfoJob?.cancel()
        getUserInfoJob = coroutineScope.launch {
            getUserDataUseCase().collect { result ->
                when (result) {
                    is ResultModel.Success -> {
                        setState(
                            currentState.copy(
                                user = result.result,
                                id = result.result.id,
                                name = result.result.name,
                                email = result.result.email,
                                gender = result.result.gender,
                            )
                        )
                        setEffect(ViewEffect.GetUserDataSuccess)
                    }
                    is ResultModel.Error -> {
                        setEffect(ViewEffect.GetUserDataFail(result.t.message ?: "Unknown error"))
                    }
                }
            }
        }
    }

    private fun changeUserInfo(userParam: UserParam) {
        changeUserJob?.cancel()
        changeUserJob = coroutineScope.launch {
            changeUserDataUseCase(userParam).collect { result ->
                when (result) {
                    is ResultModel.Success -> {
                        setState(
                            currentState.copy(
                                id = result.result.id,
                                name = result.result.name,
                                email = result.result.email,
                                gender = result.result.gender
                            )
                        )
                        setEffect(ViewEffect.ChangeUserInfoSuccess)
                    }
                    is ResultModel.Error -> setEffect(
                        ViewEffect.ChangeUserInfoFail(
                            result.t.message ?: "Unknown error"
                        )
                    )
                }
            }
        }
    }

    private fun logout() {
        logoutJob?.cancel()
        logoutJob = coroutineScope.launch {
            logoutUseCase().collect {
                setState(
                    currentState.copy(
                        loggedIn = it
                    )
                )
            }
        }
    }

    private fun validateChangePassword(
        currentPassword: String,
        newPassword: String,
        confirmedPassword: String,
    ): Boolean {
        if (currentPassword.isEmpty()) {
            setState(
                currentState.copy(
                    currentPasswordError = "Password cannot be empty"
                )
            )
            return false
        }
        if (newPassword.isEmpty()) {
            setState(
                currentState.copy(
                    newPasswordError = "Password cannot be empty"
                )
            )
            return false
        }
        if (confirmedPassword.isEmpty()) {
            setState(
                currentState.copy(
                    confirmedPasswordError = "Password cannot be empty"
                )
            )
            return false
        }
        if (newPassword.length < 8) {
            setState(
                currentState.copy(
                    newPasswordError = "Password must be longer than 8 characters"
                )
            )
            return false
        }
        if (confirmedPassword != newPassword) {
            setState(
                currentState.copy(
                    confirmedPasswordError = "Confirmed password is not the same as password"
                )
            )
            return false
        }
        return true
    }

    private fun changePassword(
        loginParam: LoginParam,
        newPassword: String,
        confirmedPassword: String,
    ) {
        changePasswordJob?.cancel()
        val validate = validateChangePassword(loginParam.password, newPassword, confirmedPassword)
        if (!validate) return
        changePasswordJob = coroutineScope.launch {
            changePasswordUseCase(loginParam, newPassword).collect { result ->
                when (result) {
                    is ResultModel.Success -> setEffect(ViewEffect.ChangePasswordSuccess)
                    is ResultModel.Error -> setEffect(
                        ViewEffect.ChangePasswordFail(
                            result.t.message ?: "Unknown error"
                        )
                    )
                }
            }
        }
    }

    override fun onEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.GetUserData -> getUser()
            is ViewEvent.ChangeUserInfo -> changeUserInfo(event.userParam)
            is ViewEvent.ChangePassword -> changePassword(
                event.loginParam,
                event.newPassword,
                event.confirmedPassword
            )
            is ViewEvent.Logout -> logout()
        }
    }

    data class ViewState(
        val user: UserModel? = null,
        val loggedIn: Boolean = true,
        val id: String? = null,
        val name: String? = null,
        val email: String? = null,
        val gender: String? = null,
        val currentPasswordError: String? = null,
        val newPasswordError: String? = null,
        val confirmedPasswordError: String? = null,

        ) : BaseViewState

    sealed interface ViewEvent : BaseViewEvent {
        object GetUserData : ViewEvent
        data class ChangeUserInfo(val userParam: UserParam) : ViewEvent
        data class ChangePassword(
            val loginParam: LoginParam,
            val newPassword: String,
            val confirmedPassword: String,
        ) :
            ViewEvent

        object Logout : ViewEvent
    }

    sealed interface ViewEffect : BaseViewEffect {
        object GetUserDataSuccess : ViewEffect
        data class GetUserDataFail(val error: String) : ViewEffect

        object ChangeUserInfoSuccess : ViewEffect
        data class ChangeUserInfoFail(val error: String) : ViewEffect
        object ChangePasswordSuccess : ViewEffect
        data class ChangePasswordFail(val error: String) : ViewEffect
    }

}