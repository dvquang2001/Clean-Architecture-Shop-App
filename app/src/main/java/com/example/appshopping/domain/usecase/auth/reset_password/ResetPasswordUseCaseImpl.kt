package com.example.appshopping.domain.usecase.auth.reset_password

import com.example.appshopping.domain.repository.AuthRepository
import javax.inject.Inject

class ResetPasswordUseCaseImpl @Inject constructor(private val authRepository: AuthRepository):
    ResetPasswordUseCase {

    override suspend fun invoke(param: ResetPasswordParam) {
        return authRepository.resetPassword(param)
    }
}