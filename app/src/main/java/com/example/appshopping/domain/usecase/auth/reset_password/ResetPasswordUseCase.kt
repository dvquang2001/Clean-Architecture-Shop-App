package com.example.appshopping.domain.usecase.auth.reset_password

interface ResetPasswordUseCase {
    suspend operator fun invoke(param: ResetPasswordParam)
}