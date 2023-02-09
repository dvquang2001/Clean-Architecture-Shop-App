package com.example.appshopping.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.appshopping.data.dto.LoginDto
import com.example.appshopping.data.dto.ProductDto
import com.example.appshopping.data.dto.UserDto
import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.auth.LoginModel
import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.domain.repository.MainRepository
import com.example.appshopping.domain.usecase.auth.login.LoginParam
import com.example.appshopping.domain.usecase.main.add_product.ProductParam
import com.example.appshopping.domain.usecase.main.get_user.UserParam
import com.example.appshopping.other.Constant.USER_DATA
import com.example.appshopping.other.Constant.USER_DATA_LOGIN
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import com.google.gson.Gson
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(
    private val sharePreferences: SharedPreferences,
    @Named("product")
    private val productCollectionReference: CollectionReference,
    @Named("user")
    private val userCollectionReference: CollectionReference,
    private val gson: Gson,
) : MainRepository {

    override fun addProduct(productParam: ProductParam) {

    }

    private fun getNewPersonMap(name: String, gender: String): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        if (name.isNotEmpty()) {
            map["name"] = name
        }
        if (gender.isNotEmpty()) {
            map["gender"] = gender
        }
        return map
    }

    override fun changerUserInfo(userParam: UserParam): Flow<ResultModel<UserModel>> {
        return callbackFlow {
            try {
                val userQuery = userCollectionReference.whereEqualTo("id", userParam.id)
                    .get().await()
                if (userQuery.documents.isNotEmpty()) {
                    for (document in userQuery) {
                        try {
                            userCollectionReference.document(document.id).set(
                                getNewPersonMap(userParam.name, userParam.gender),
                                SetOptions.merge()
                            ).await()
                            val userFromStorage = getUserInfoFromStorage()
                            var userDto: UserDto? = null
                            if (userParam.name.isNotEmpty()) {
                                userDto = UserDto(
                                    id = userParam.id,
                                    name = userParam.name,
                                    gender = userFromStorage?.gender,
                                    email = userFromStorage?.email,
                                    accountBalance = userFromStorage?.accountBalance
                                )
                            }
                            if (userParam.gender.isNotEmpty()) {
                                userDto = UserDto(
                                    id = userParam.id,
                                    name = userFromStorage?.name,
                                    gender = userParam.gender,
                                    email = userFromStorage?.email,
                                    accountBalance = userFromStorage?.accountBalance
                                )
                            }
                            saveUserInfoToStorage(userDto!!)
                            trySend(ResultModel.Success(userDto.toUserModel()))
                        } catch (e: Exception) {
                            trySend(ResultModel.Error(e))
                        }
                    }
                }
            } catch (e: Exception) {
                trySend(ResultModel.Error(e))
            }
            awaitClose()
        }
    }

    override fun getProduct(id: String): Flow<ProductModel> {
        return flow {
            try {
                val querySnapshot = productCollectionReference.whereEqualTo("id", id).get().await()
                for (document in querySnapshot.documents) {
                    val product = document.toObject<ProductDto>()
                    if (product != null) {
                        emit(product.toProductModel())
                    }
                }
            } catch (e: Exception) {
                Log.d("Main", e.toString())
            }
        }
    }

    override fun getProducts(): Flow<List<ProductModel>> {
        return flow {
            try {
                val querySnapshot = productCollectionReference.get().await()
                val list = mutableListOf<ProductModel>()
                for (document in querySnapshot.documents) {
                    val product = document.toObject<ProductDto>()
                    product?.let {
                        list.add(product.toProductModel())
                    }
                }
                if (list.isNotEmpty()) {
                    emit(list)
                }
            } catch (e: Exception) {
                Log.d("Main", e.toString())
            }

        }
    }

    private fun saveUserInfoToStorage(user: UserDto) {
        val jsonString = gson.toJson(user)
        sharePreferences.edit().apply {
            this.putString(USER_DATA, jsonString)
        }.apply()
    }

    private fun getUserInfoFromStorage(): UserDto? {
        val userDataString = sharePreferences.getString(USER_DATA, null)
        return gson.fromJson(userDataString, UserDto::class.java)
    }

    private fun getUserFromLogin(): LoginDto? {
        val userDataString = sharePreferences.getString(USER_DATA_LOGIN, null)
        return gson.fromJson(userDataString, LoginDto::class.java)
    }

    override suspend fun saveUserToStorage() {
        val userLogin = getUserFromLogin()
        val querySnapshot = userCollectionReference.whereEqualTo("id", userLogin?.id).get().await()
        for (document in querySnapshot.documents) {
            val userFromServer = document.toObject(UserDto::class.java)
            if (userFromServer != null) {
                saveUserInfoToStorage(userFromServer)
            }
        }
    }

    private fun saveUserLoginToStorage(loginDto: LoginDto) {
        val jsonString = gson.toJson(loginDto)
        sharePreferences.edit().apply {
            this.putString(USER_DATA_LOGIN, jsonString)
        }.apply()
    }

    override fun changePassword(
        loginParam: LoginParam,
        newPassword: String,
    ): Flow<ResultModel<LoginModel>> {
        return callbackFlow {
            val currentUser = FirebaseAuth.getInstance().currentUser
            val credential = EmailAuthProvider.getCredential(loginParam.email, loginParam.password)
            currentUser?.reauthenticate(credential)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    currentUser.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            val loginDto = LoginDto(loginParam.email,newPassword)
                            saveUserLoginToStorage(loginDto)
                            trySend(ResultModel.Success(loginDto.toLoginModel()))
                        } else {
                            updateTask.exception?.let {
                                val result = ResultModel.Error(it)
                                trySend(result)
                            }
                        }
                    }
                } else {
                    task.exception?. let {
                        val result = ResultModel.Error(it)
                        trySend(result)
                    }
                }
            }
            awaitClose()
        }
    }

    override fun getCurrentUserInfo(): Flow<UserModel?> {
        return flow {
            try {
                val userDto = getUserInfoFromStorage()
                emit(userDto?.toUserModel())
            } catch (e: Exception) {
                Log.d("Main", e.toString())
            }
        }
    }

    override fun getProductsByUserId(listProductId: List<String>): Flow<List<ProductModel>> {
        return flow {
            try {
                val listProduct = mutableListOf<ProductModel>()
                for(id in listProductId) {
                    val querySnapshot = productCollectionReference.whereEqualTo("id", id).get().await()
                    for (document in querySnapshot.documents) {
                        val product = document.toObject<ProductDto>()
                        if (product != null) {
                            listProduct.add(product.toProductModel())
                        }
                    }
                }
                emit(listProduct)
            } catch (e: Exception) {
                Log.d("Main", e.toString())
            }
        }
    }
}