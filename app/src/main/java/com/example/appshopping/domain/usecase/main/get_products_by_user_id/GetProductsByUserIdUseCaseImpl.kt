package com.example.appshopping.domain.usecase.main.get_products_by_user_id

import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsByUserIdUseCaseImpl @Inject constructor(private val mainRepository: MainRepository)
    : GetProductsByUserIdUseCase{

    override fun invoke(listProductId: List<String>): Flow<List<ProductModel>> {
        return mainRepository.getProductsByUserId(listProductId)
    }
}