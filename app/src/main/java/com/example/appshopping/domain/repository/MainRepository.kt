package com.example.appshopping.domain.repository

import com.example.appshopping.domain.model.ResultModel
import com.example.appshopping.domain.model.auth.LoginModel
import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.domain.model.main.UserModel
import com.example.appshopping.domain.usecase.auth.login.LoginParam
import com.example.appshopping.domain.usecase.main.get_user.UserParam
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun saveUserDataToStorage()
    fun getProduct(id: String): Flow<ProductModel>
    fun getProducts(): Flow<List<ProductModel>>
    fun getCartProducts(listProductId: List<String>): Flow<List<ProductModel>>
    fun getCurrentUserData(): Flow<ResultModel<UserModel>>
    fun addProductToCart(cartProductId: String): Flow<ResultModel<UserModel>>
    fun changeUserData(userParam: UserParam): Flow<ResultModel<UserModel>>
    fun changePassword(loginParam: LoginParam, newPassword: String): Flow<ResultModel<LoginModel>>
    fun deleteProductFromCart(cartProductId: String): Flow<ResultModel<UserModel>>
    fun pay(cartProductIds: List<String>,newUserAccountBalance: String): Flow<ResultModel<UserModel>>
    fun buy(cartProductId: String,productPrice: String): Flow<ResultModel<UserModel>>
    fun testGetProducts(): Flow<List<ProductModel>>

}