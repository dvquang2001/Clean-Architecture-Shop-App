package com.example.appshopping.domain.usecase.main.change_password

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.auth.LoginModel
import com.example.appshopping.domain.repository.MainRepository
import com.example.appshopping.domain.usecase.auth.login.LoginParam
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangePasswordUseCaseImpl @Inject constructor(private val mainRepository: MainRepository)
    : ChangePasswordUseCase{

    override fun invoke(loginParam: LoginParam, newPassword: String): Flow<ResultModel<LoginModel>> {
        return mainRepository.changePassword(loginParam,newPassword)
    }
}