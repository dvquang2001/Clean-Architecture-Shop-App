package com.example.appshopping.domain.usecase.auth.init_user

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.UserModel
import kotlinx.coroutines.flow.Flow

interface InitUserUseCase {
    operator fun invoke(): Flow<ResultModel<UserModel>>
}