package com.example.appshopping.domain.usecase.auth.get_user_data

import com.example.appshopping.domain.model.auth.LoginModel
import kotlinx.coroutines.flow.Flow

interface GetUserDataUseCase {
    operator fun invoke(): Flow<LoginModel?>
}