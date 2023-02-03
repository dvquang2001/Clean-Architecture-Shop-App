package com.example.appshopping.domain.usecase.register

import com.example.appshopping.domain.model.RegisterModel
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(private val authRepository: AuthRepository) : RegisterUseCase {

    override fun invoke(param: RegisterParam): Flow<ResultModel<RegisterModel>> {
        return authRepository.register(param)
    }
}