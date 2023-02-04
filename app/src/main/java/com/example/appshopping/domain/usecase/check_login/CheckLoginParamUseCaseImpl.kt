package com.example.appshopping.domain.usecase.check_login

import com.example.appshopping.domain.repository.AuthRepository
import javax.inject.Inject

class CheckLoginParamUseCaseImpl @Inject constructor(private val authRepository: AuthRepository): CheckLoginUseCase {

    override fun invoke(): Boolean {
        return authRepository.isLogin()
    }
}