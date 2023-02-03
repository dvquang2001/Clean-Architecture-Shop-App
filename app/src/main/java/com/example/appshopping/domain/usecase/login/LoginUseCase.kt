package com.example.appshopping.domain.usecase.login

import com.example.appshopping.domain.model.LoginModel
import com.example.appshopping.domain.model.ResultModel
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {

    operator fun invoke(param: LoginParam): Flow<ResultModel<LoginModel>>
}