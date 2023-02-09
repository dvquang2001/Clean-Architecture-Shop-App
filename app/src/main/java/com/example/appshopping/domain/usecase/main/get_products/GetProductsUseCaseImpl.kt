package com.example.appshopping.domain.usecase.main.get_products

import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository,
) : GetProductsUseCase {

    override fun invoke(): Flow<List<ProductModel>> {
        return mainRepository.getProducts()
    }
}