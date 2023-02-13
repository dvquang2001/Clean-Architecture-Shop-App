package com.example.appshopping.domain.usecase.pay

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PayUseCaseImpl @Inject constructor(private val mainRepository: MainRepository)
    : PayUseCase{

    override fun invoke(cartProductIds: List<String>,newUserAccountBalance: String): Flow<ResultModel<UserModel>> {
        return mainRepository.pay(cartProductIds,newUserAccountBalance)
    }
}