package com.example.appshopping.domain.usecase.auth.logout

import android.util.Log
import com.example.appshopping.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogoutUseCaseImpl @Inject constructor(private val authRepository: AuthRepository)
    : LogoutUseCase {
    override operator fun invoke(): Flow<Boolean> {
        return authRepository.logout()
    }
}