package com.example.appshopping.domain.usecase.getUserData

import com.example.appshopping.domain.model.LoginModel
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.repository.AuthRepository
import com.example.appshopping.domain.usecase.login.LoginParam
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDataUseCaseImpl @Inject constructor(private val authRepository: AuthRepository): GetUserDataUseCase {

    override fun invoke(param: LoginParam): Flow<ResultModel<LoginModel>> {
        return authRepository.getCurrentUser()
    }
}