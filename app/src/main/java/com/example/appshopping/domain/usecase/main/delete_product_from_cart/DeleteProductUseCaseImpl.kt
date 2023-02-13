package com.example.appshopping.domain.usecase.main.delete_product_from_cart

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteProductUseCaseImpl @Inject constructor(private val mainRepository: MainRepository)
    : DeleteProductUseCase{
    override fun invoke(productId: String): Flow<ResultModel<UserModel>> {
        return mainRepository.deleteProductFromCart(productId)
    }
}