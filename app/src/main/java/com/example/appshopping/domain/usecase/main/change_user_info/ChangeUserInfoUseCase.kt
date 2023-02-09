package com.example.appshopping.domain.usecase.main.change_user_info

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.domain.usecase.main.get_user.UserParam
import kotlinx.coroutines.flow.Flow

interface ChangeUserInfoUseCase {
    operator fun invoke(userParam: UserParam): Flow<ResultModel<UserModel>>
}