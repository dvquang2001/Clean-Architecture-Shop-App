package com.example.appshopping.domain.usecase.auth.init_user

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InitUserUseCaseImpl @Inject constructor(private val authRepository: AuthRepository): InitUserUseCase {
    override fun invoke(): Flow<ResultModel<UserModel>> {
        return authRepository.initializeUserData()
    }
}