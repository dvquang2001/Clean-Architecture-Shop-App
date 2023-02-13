package com.example.appshopping.domain.usecase.main.delete_product_from_cart

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.UserModel
import kotlinx.coroutines.flow.Flow

interface DeleteProductUseCase {
    operator fun invoke(productId: String): Flow<ResultModel<UserModel>>
}