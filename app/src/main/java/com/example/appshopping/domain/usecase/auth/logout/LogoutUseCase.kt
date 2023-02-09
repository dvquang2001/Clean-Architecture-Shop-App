package com.example.appshopping.domain.usecase.auth.logout

import kotlinx.coroutines.flow.Flow

interface LogoutUseCase {
    operator fun invoke(): Flow<Boolean>
}