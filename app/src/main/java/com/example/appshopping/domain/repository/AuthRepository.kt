package com.example.appshopping.domain.repository

import com.example.appshopping.domain.model.LoginModel
import com.example.appshopping.domain.model.RegisterModel
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.usecase.login.LoginParam
import com.example.appshopping.domain.usecase.register.RegisterParam
import com.example.appshopping.domain.usecase.reset_password.ResetPasswordParam
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun login(param: LoginParam): Flow<ResultModel<LoginModel>>

    fun register(param: RegisterParam): Flow<ResultModel<RegisterModel>>

    fun isLogin(): Boolean

    fun getCurrentUser(): Flow<ResultModel<LoginModel>>

    suspend fun resetPassword(param: ResetPasswordParam)
}