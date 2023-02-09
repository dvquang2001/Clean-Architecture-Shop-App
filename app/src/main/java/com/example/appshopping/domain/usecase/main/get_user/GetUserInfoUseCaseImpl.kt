package com.example.appshopping.domain.usecase.main.get_user

import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUseCaseImpl @Inject constructor(private val mainRepository: MainRepository) :
    GetUserInfoUseCase {

    override fun invoke(): Flow<UserModel?> {
        return mainRepository.getCurrentUserInfo()
    }
}