package com.example.appshopping.domain.usecase.main.change_user_info

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.repository.MainRepository
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.domain.usecase.main.get_user.UserParam
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangeUserInfoUseCaseImpl @Inject constructor(private val mainRepository: MainRepository) :
    ChangeUserInfoUseCase {
    override fun invoke(userParam: UserParam): Flow<ResultModel<UserModel>> {
        return mainRepository.changerUserInfo(userParam)
    }
}