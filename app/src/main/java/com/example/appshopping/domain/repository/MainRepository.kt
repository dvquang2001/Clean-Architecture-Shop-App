package com.example.appshopping.domain.repository

import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.domain.usecase.main.add_product.ProductParam
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun addProduct(productParam: ProductParam)

    suspend fun getProducts(): Flow<List<ProductModel>>

}