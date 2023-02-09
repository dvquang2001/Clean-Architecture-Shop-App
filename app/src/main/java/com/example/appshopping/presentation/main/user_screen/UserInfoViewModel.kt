package com.example.appshopping.presentation.main.user_screen

import android.util.Log
import com.example.appshopping.base.BaseViewEffect
import com.example.appshopping.base.BaseViewEvent
import com.example.appshopping.base.BaseViewModel
import com.example.appshopping.base.BaseViewState
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.usecase.main.change_user_info.ChangeUserInfoUseCase
import com.example.appshopping.domain.usecase.main.get_user.GetUserInfoUseCase
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.domain.usecase.auth.login.LoginParam
import com.example.appshopping.domain.usecase.main.change_password.ChangePasswordUseCase
import com.example.appshopping.domain.usecase.main.get_user.UserParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val changeUserInfoUseCase: ChangeUserInfoUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase
) :
    BaseViewModel<UserInfoViewModel.ViewState, UserInfoViewModel.ViewEvent, UserInfoViewModel.ViewEffect>(
        ViewState()
    ) {

    private var getUserInfoJob: Job? = null
    private var changeUserJob: Job? = null
    private var changePasswordJob: Job? = null

    private fun getUser() {
        getUserInfoJob?.cancel()
        getUserInfoJob = coroutineScope.launch {
            getUserInfoUseCase().collect { user ->
                setState(
                    currentState.copy(
                        id = user?.id,
                        name = user?.name,
                        email = user?.email,
                        gender = user?.gender
                    )
                )
            }
        }
    }

    private fun changeUserInfo(userParam: UserParam) {
        changeUserJob?.cancel()
        changeUserJob = coroutineScope.launch {
            changeUserInfoUseCase(userParam).collect { result ->
                when(result) {
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
                    is ResultModel.Error -> setEffect(ViewEffect.ChangeUserInfoFail(result.t.message ?: "Unknown error"))
                }
            }
        }
    }

    private fun validateChangePassword(
        currentPassword: String,
        newPassword: String,
        confirmedPassword: String
    ) : Boolean{
        if(currentPassword.isEmpty()) {
            setState(
                currentState.copy(
                    currentPasswordError = "Password cannot be empty"
                )
            )
            return false
        }
        if(newPassword.isEmpty()) {
            setState(
                currentState.copy(
                    newPasswordError = "Password cannot be empty"
                )
            )
            return false
        }
        if(confirmedPassword.isEmpty()) {
            setState(
                currentState.copy(
                    confirmedPasswordError = "Password cannot be empty"
                )
            )
            return false
        }
        if(newPassword.length < 8) {
            setState(
                currentState.copy(
                    newPasswordError = "Password must be longer than 8 characters"
                )
            )
            return false
        }
        if(confirmedPassword != newPassword) {
            setState(
                currentState.copy(
                    confirmedPasswordError =  "Confirmed password is not the same as password"
                )
            )
            return false
        }
        return true
    }

    private fun changePassword(loginParam: LoginParam,newPassword: String,confirmedPassword: String) {
        changePasswordJob?.cancel()
        val validate = validateChangePassword(loginParam.password,newPassword,confirmedPassword)
        if(!validate) return
        changePasswordJob = coroutineScope.launch {
            changePasswordUseCase(loginParam,newPassword).collect { result ->
                when(result) {
                    is ResultModel.Success -> setEffect(ViewEffect.ChangePasswordSuccess)
                    is ResultModel.Error -> setEffect(ViewEffect.ChangePasswordFail(result.t.message ?: "Unknown error"))
                }
            }
        }
    }

    override fun onEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.CheckUserData -> getUser()
            is ViewEvent.ChangeUserInfo -> changeUserInfo(event.userParam)
            is ViewEvent.ChangePassword -> changePassword(event.loginParam,event.newPassword,event.confirmedPassword)
        }
    }

    data class ViewState(
        val id: String? = null,
        val name: String? = null,
        val email: String? = null,
        val gender: String? = null,
        val currentPasswordError: String? = null,
        val newPasswordError: String? = null,
        val confirmedPasswordError: String? = null

    ) : BaseViewState

    sealed interface ViewEvent : BaseViewEvent {
        object CheckUserData : ViewEvent
        data class ChangeUserInfo(val userParam: UserParam) : ViewEvent
        data class ChangePassword(val loginParam: LoginParam,val newPassword: String,val confirmedPassword: String): ViewEvent
    }

    sealed interface ViewEffect : BaseViewEffect {
        object ChangeUserInfoSuccess: ViewEffect
        data class ChangeUserInfoFail(val error: String) : ViewEffect
        object ChangePasswordSuccess: ViewEffect
        data class ChangePasswordFail(val error: String): ViewEffect
    }

}