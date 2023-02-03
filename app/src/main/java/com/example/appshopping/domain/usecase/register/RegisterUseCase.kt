package com.example.appshopping.domain.usecase.register

import com.example.appshopping.domain.model.RegisterModel
import com.example.appshopping.domain.model.ResultModel
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {

    operator fun invoke(param: RegisterParam): Flow<ResultModel<RegisterModel>>
}