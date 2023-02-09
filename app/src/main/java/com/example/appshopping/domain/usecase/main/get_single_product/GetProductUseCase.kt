package com.example.appshopping.domain.usecase.main.get_single_product

import com.example.appshopping.domain.model.main.ProductModel
import kotlinx.coroutines.flow.Flow

interface GetProductUseCase {
     operator fun invoke(id: String) : Flow<ProductModel>
}