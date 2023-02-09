package com.example.appshopping.domain.usecase.main.get_products_by_user_id

import com.example.appshopping.domain.model.main.ProductModel
import kotlinx.coroutines.flow.Flow

interface GetProductsByUserIdUseCase {
    operator fun invoke(listProductId: List<String>): Flow<List<ProductModel>>
}