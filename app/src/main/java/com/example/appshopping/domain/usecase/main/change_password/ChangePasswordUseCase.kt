package com.example.appshopping.domain.usecase.main.change_password

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.auth.LoginModel
import com.example.appshopping.domain.usecase.auth.login.LoginParam
import kotlinx.coroutines.flow.Flow

interface ChangePasswordUseCase {
    operator fun invoke(loginParam: LoginParam,newPassword: String): Flow<ResultModel<LoginModel>>
}