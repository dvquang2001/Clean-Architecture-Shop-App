package com.example.appshopping.domain.usecase.main.get_user

import com.example.appshopping.domain.model.main.UserModel
import kotlinx.coroutines.flow.Flow


interface GetUserInfoUseCase {
    operator fun invoke(): Flow<UserModel?>
}