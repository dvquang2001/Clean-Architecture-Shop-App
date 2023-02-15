package com.example.appshopping.domain.usecase.main.buy

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BuyUseCaseImpl @Inject constructor(private val mainRepository: MainRepository): BuyUseCase {
    override fun invoke(
        cartProductId: String,
        productPrice: String,
    ): Flow<ResultModel<UserModel>> {
        return mainRepository.buy(cartProductId,productPrice)
    }
}