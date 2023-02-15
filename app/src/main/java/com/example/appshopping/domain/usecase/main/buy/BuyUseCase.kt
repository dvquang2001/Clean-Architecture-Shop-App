package com.example.appshopping.domain.usecase.main.buy

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.UserModel
import kotlinx.coroutines.flow.Flow

interface BuyUseCase {
    operator fun invoke(cartProductId: String,productPrice: String): Flow<ResultModel<UserModel>>
}