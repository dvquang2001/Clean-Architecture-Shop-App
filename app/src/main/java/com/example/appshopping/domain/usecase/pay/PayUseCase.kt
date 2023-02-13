package com.example.appshopping.domain.usecase.pay

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.UserModel
import kotlinx.coroutines.flow.Flow

interface PayUseCase {
    operator fun invoke(cartProductIds: List<String>,newUserAccountBalance: String): Flow<ResultModel<UserModel>>
}