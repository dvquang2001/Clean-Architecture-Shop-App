package com.example.appshopping.domain.usecase.main.add_product

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.UserModel
import kotlinx.coroutines.flow.Flow

interface AddProductToCartUseCase {
    operator fun invoke(productId: String): Flow<ResultModel<UserModel>>
}