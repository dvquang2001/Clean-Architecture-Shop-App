package com.example.appshopping.domain.usecase.main.get_user

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.UserModel
import kotlinx.coroutines.flow.Flow


interface GetUserDataUseCase {
    operator fun invoke(): Flow<ResultModel<UserModel>>
}