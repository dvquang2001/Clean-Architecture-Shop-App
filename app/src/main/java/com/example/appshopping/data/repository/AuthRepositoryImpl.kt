package com.example.appshopping.data.repository

import android.app.Application
import com.example.appshopping.data.dto.LoginDto
import com.example.appshopping.data.dto.RegisterDto
import com.example.appshopping.domain.model.LoginModel
import com.example.appshopping.domain.model.RegisterModel
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.repository.AuthRepository
import com.example.appshopping.domain.usecase.login.LoginParam
import com.example.appshopping.domain.usecase.register.RegisterParam
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(application: Application) : AuthRepository {

    override fun login(param: LoginParam): Flow<ResultModel<LoginModel>> {
        return callbackFlow {
            Firebase.auth.signInWithEmailAndPassword(param.email, param.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = task.result.user
                        if (firebaseUser != null) {
                            val loginDto = LoginDto(
                                id = firebaseUser.uid,
                                email = firebaseUser.email ?: ""
                            )
                            val model = loginDto.toLoginModel()
                            trySend(ResultModel.Success(model))
                        } else {
                            val result = ResultModel.Error(Exception())
                            trySend(result)
                        }
                    } else {
                        task.exception?.let {
                            val result = ResultModel.Error(it)
                            trySend(result)
                        }
                    }
                }
            awaitClose()
        }

    }

    override fun register(param: RegisterParam): Flow<ResultModel<RegisterModel>> {
        return callbackFlow {
            Firebase.auth.createUserWithEmailAndPassword(param.email, param.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = task.result.user
                        if (firebaseUser != null) {
                            val registerDto = RegisterDto(
                                id = firebaseUser.uid,
                                email = firebaseUser.email ?: ""
                            )
                            val model = registerDto.toRegisterModel()
                            trySend(ResultModel.Success(model))
                        } else {
                            val result = ResultModel.Error(Exception())
                            trySend(result)
                        }
                    } else {
                        task.exception?.let {
                            val result = ResultModel.Error(it)
                            trySend(result)
                        }
                    }
                }
        }
    }

    override fun resetPassword(email: String) {
        TODO("Not yet implemented")
    }
}