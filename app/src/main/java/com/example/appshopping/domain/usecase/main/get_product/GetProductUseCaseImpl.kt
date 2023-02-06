package com.example.appshopping.domain.usecase.main.get_product

import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository
) : GetProductUseCase{

    override suspend fun invoke(): Flow<List<ProductModel>> {
        return mainRepository.getProducts()
    }
}