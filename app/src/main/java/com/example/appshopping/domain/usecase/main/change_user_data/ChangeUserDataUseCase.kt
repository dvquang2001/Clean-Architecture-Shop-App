package com.example.appshopping.domain.usecase.main.change_user_data

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.domain.usecase.main.get_user.UserParam
import kotlinx.coroutines.flow.Flow

interface ChangeUserDataUseCase {
    operator fun invoke(userParam: UserParam): Flow<ResultModel<UserModel>>
}