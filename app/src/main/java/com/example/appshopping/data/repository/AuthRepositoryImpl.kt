package com.example.appshopping.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.appshopping.data.dto.LoginDto
import com.example.appshopping.data.dto.RegisterDto
import com.example.appshopping.domain.model.auth.LoginModel
import com.example.appshopping.domain.model.auth.RegisterModel
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.repository.AuthRepository
import com.example.appshopping.domain.usecase.auth.login.LoginParam
import com.example.appshopping.domain.usecase.auth.register.RegisterParam
import com.example.appshopping.domain.usecase.auth.reset_password.ResetPasswordParam
import com.example.appshopping.other.Constant
import com.example.appshopping.other.Constant.LOGGED_IN
import com.example.appshopping.other.Constant.USER_DATA_LOGIN
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val sharePreferences: SharedPreferences,
    private val gson: Gson
) : AuthRepository {

    private fun saveUserDataToStorage(loginDto: LoginDto) {
        val jsonString = gson.toJson(loginDto)
        sharePreferences.edit().apply {
            this.putString(USER_DATA_LOGIN, jsonString)
        }.apply()
    }
    private var isLogin = false

    private fun getUserDataFromStorage(): LoginDto? {
        val userDataString = sharePreferences.getString(USER_DATA_LOGIN, null)
        return gson.fromJson(userDataString, LoginDto::class.java)
    }

    private fun saveLoginState(){
        isLogin = true
        sharePreferences.edit().apply {
            this.putBoolean(LOGGED_IN,isLogin)
        }.apply()
    }

    override fun isLogin() : Boolean{
        return sharePreferences.getBoolean(LOGGED_IN,false)
    }

    override fun getCurrentUser(): Flow<LoginModel?> {
        return flow {
            val userData = getUserDataFromStorage()
            if(userData != null) {
                val loginModel = userData.toLoginModel()
                emit(loginModel)
            } else {
                emit(null)
            }
        }
    }

    override suspend fun resetPassword(param: ResetPasswordParam) {
        Firebase.auth.sendPasswordResetEmail(param.email)
    }

    override fun login(param: LoginParam): Flow<ResultModel<LoginModel>> {
        return callbackFlow {
            trySend(ResultModel.Loading())
            Firebase.auth.signInWithEmailAndPassword(param.email, param.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = task.result.user
                        if (firebaseUser != null) {
                            val loginDto = LoginDto(
                                id = firebaseUser.uid,
                                email = firebaseUser.email ?: ""
                            )
                            saveUserDataToStorage(loginDto)
                            saveLoginState()
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
            trySend(ResultModel.Loading())
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
                            val loginDto = LoginDto(registerDto.id,registerDto.email)
                            saveUserDataToStorage(loginDto)
                            saveLoginState()
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

    override fun logout(): Flow<Boolean> {
        return flow {
            isLogin = false
            sharePreferences.edit().apply {
                this.putBoolean(Constant.LOGGED_IN,false)
            }.apply()
            emit(isLogin)
        }
    }


}