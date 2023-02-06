package com.example.appshopping.domain.usecase.auth.register

import com.example.appshopping.domain.model.auth.RegisterModel
import com.example.appshopping.domain.model.ResultModel
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {

    operator fun invoke(param: RegisterParam): Flow<ResultModel<RegisterModel>>
}