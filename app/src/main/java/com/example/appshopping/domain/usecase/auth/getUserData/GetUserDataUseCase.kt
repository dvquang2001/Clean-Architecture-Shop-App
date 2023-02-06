package com.example.appshopping.domain.usecase.auth.getUserData

import com.example.appshopping.domain.model.auth.LoginModel
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.usecase.auth.login.LoginParam
import kotlinx.coroutines.flow.Flow

interface GetUserDataUseCase {
    operator fun invoke(): Flow<LoginModel?>
}