package com.example.appshopping.domain.usecase.auth.login

import com.example.appshopping.domain.model.auth.LoginModel
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(private val authRepository: AuthRepository):
    LoginUseCase {

    override fun invoke(param: LoginParam): Flow<ResultModel<LoginModel>> {
        return authRepository.login(param)
    }
}