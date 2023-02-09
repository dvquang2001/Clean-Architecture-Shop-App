package com.example.appshopping.domain.usecase.main.save_user

import com.example.appshopping.domain.repository.MainRepository
import javax.inject.Inject

class SaveUserUseCaseImpl @Inject constructor(private val mainRepository: MainRepository)
    : SaveUserUseCase{

    override suspend fun invoke() {
        mainRepository.saveUserToStorage()
    }
}