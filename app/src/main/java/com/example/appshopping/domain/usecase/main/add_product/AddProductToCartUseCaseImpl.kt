package com.example.appshopping.domain.usecase.main.add_product

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddProductToCartUseCaseImpl @Inject constructor(private val mainRepository: MainRepository)
    :AddProductToCartUseCase{
    override fun invoke(productId: String): Flow<ResultModel<UserModel>> {
        return mainRepository.addProductToCart(productId)
    }
}