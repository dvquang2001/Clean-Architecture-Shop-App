package com.example.appshopping.domain.usecase.auth.getUserData

import com.example.appshopping.domain.model.auth.LoginModel
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.repository.AuthRepository
import com.example.appshopping.domain.usecase.auth.login.LoginParam
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDataUseCaseImpl @Inject constructor(private val authRepository: AuthRepository):
    GetUserDataUseCase {

    override fun invoke(): Flow<LoginModel?> {
        return authRepository.getCurrentUser()
    }
}