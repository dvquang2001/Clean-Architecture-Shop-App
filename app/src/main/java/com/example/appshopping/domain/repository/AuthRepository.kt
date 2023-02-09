package com.example.appshopping.domain.repository

import com.example.appshopping.domain.model.auth.LoginModel
import com.example.appshopping.domain.model.auth.RegisterModel
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.usecase.auth.login.LoginParam
import com.example.appshopping.domain.usecase.auth.register.RegisterParam
import com.example.appshopping.domain.usecase.auth.reset_password.ResetPasswordParam
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun login(param: LoginParam): Flow<ResultModel<LoginModel>>

    fun register(param: RegisterParam): Flow<ResultModel<RegisterModel>>

    fun isLogin(): Boolean

    fun getCurrentUser(): Flow<LoginModel?>

    fun logout(): Flow<Boolean>

    suspend fun resetPassword(param: ResetPasswordParam)
}