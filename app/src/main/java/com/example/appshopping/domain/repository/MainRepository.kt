package com.example.appshopping.domain.repository

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.auth.LoginModel
import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.domain.usecase.main.add_product.ProductParam
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.domain.usecase.auth.login.LoginParam
import com.example.appshopping.domain.usecase.main.get_user.UserParam
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun addProduct(productParam: ProductParam)

     fun getProducts(): Flow<List<ProductModel>>

     fun getCurrentUserInfo(): Flow<UserModel?>

     fun getProduct(id: String): Flow<ProductModel>

     fun changerUserInfo(userParam: UserParam): Flow<ResultModel<UserModel>>

     fun changePassword(loginParam: LoginParam,newPassword: String): Flow<ResultModel<LoginModel>>

    suspend fun saveUserToStorage()

}