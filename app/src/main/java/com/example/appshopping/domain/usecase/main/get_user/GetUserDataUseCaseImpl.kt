package com.example.appshopping.domain.usecase.main.get_user

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDataUseCaseImpl @Inject constructor(private val mainRepository: MainRepository) :
    GetUserDataUseCase {

    override fun invoke(): Flow<ResultModel<UserModel>> {
        return mainRepository.getCurrentUserData()
    }
}