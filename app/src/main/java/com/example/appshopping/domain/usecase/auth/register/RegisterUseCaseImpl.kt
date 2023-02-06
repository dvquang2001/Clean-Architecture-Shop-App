package com.example.appshopping.domain.usecase.auth.register

import android.util.Log
import com.example.appshopping.domain.model.auth.RegisterModel
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(private val authRepository: AuthRepository) :
    RegisterUseCase {

    override fun invoke(param: RegisterParam): Flow<ResultModel<RegisterModel>> {
        Log.d("Main","Success in usecase")
        return authRepository.register(param)
    }
}