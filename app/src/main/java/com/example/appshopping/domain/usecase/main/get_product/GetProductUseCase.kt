package com.example.appshopping.domain.usecase.main.get_product

import com.example.appshopping.domain.model.main.ProductModel
import kotlinx.coroutines.flow.Flow

interface GetProductUseCase {

    suspend operator fun invoke(): Flow<List<ProductModel>>
}