package com.example.appshopping.domain.usecase.getUserData

import com.example.appshopping.domain.model.LoginModel
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.usecase.login.LoginParam
import kotlinx.coroutines.flow.Flow

interface GetUserDataUseCase {
    operator fun invoke(param: LoginParam): Flow<ResultModel<LoginModel>>
}