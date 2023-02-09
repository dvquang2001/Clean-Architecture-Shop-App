package com.example.appshopping.domain.usecase.main.get_products

import com.example.appshopping.domain.model.main.ProductModel
import kotlinx.coroutines.flow.Flow

interface GetProductsUseCase {

    operator fun invoke(): Flow<List<ProductModel>>
}