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
import com.example.appshopping.domain.usecase.main.get_user.UserParam
import com.example.appshopping.other.Constant.MAIN_TAG
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

    private fun saveUserToStorage(user: UserDto) {
        val jsonString = gson.toJson(user)
        sharePreferences.edit().apply {
            this.putString(USER_DATA, jsonString)
        }.apply()
    }

    private fun getUserDataFromStorage(): UserDto? {
        val userDataString = sharePreferences.getString(USER_DATA, null)
        return gson.fromJson(userDataString, UserDto::class.java)
    }

    private fun getUserFromLogin(): LoginDto? {
        val userDataString = sharePreferences.getString(USER_DATA_LOGIN, null)
        return gson.fromJson(userDataString, LoginDto::class.java)
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

    private fun getCartProductPersonMap(cartProducts: List<String>): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["cartProducts"] = cartProducts
        return map
    }

    private fun getConfirmationProductPersonMap(confirmationProducts: List<String>,newUserAccountBalance: String): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["confirmationProducts"] = confirmationProducts
        map["accountBalance"] = newUserAccountBalance
        return map
    }

    private fun getConfirmationProductByPayPersonMap(
        cartProducts: List<String>,
        confirmationProducts: List<String>,
        newUserAccountBalance: String,
    ): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["confirmationProducts"] = confirmationProducts
        map["cartProducts"] = cartProducts
        map["accountBalance"] = newUserAccountBalance
        return map
    }

    override suspend fun saveUserDataToStorage() {
        val userLogin = getUserFromLogin()
        val querySnapshot =
            userCollectionReference.whereEqualTo("id", userLogin?.id).get().await().firstOrNull()
        val userFromServer = querySnapshot?.toObject(UserDto::class.java)
        if (userFromServer != null) {
            saveUserToStorage(userFromServer)
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
                Log.d(MAIN_TAG, "getProduct: $e")
            }
        }
    }

    override fun testGetProducts(): Flow<List<ProductModel>> {
        return callbackFlow {
            try {
                val productList = mutableListOf<ProductModel>()
               productCollectionReference.get().addOnCompleteListener {
                   if(it.isSuccessful) {
                       val list = it.result.documents
                       for(document in list) {
                           val productDto = document.toObject(ProductDto::class.java)
                           if(productDto != null) {
                               productList.add(productDto.toProductModel())
                           }
                       }
                       trySend(productList)
                   }
                   else {
                       Log.d(MAIN_TAG,"getProducts: ${it.exception}")
                   }
               }.addOnFailureListener {
                   Log.d(MAIN_TAG, "getProducts: $it")
                   trySend(listOf())
               }
            }  catch (e: Exception) {
                Log.d(MAIN_TAG, "getProducts: $e")
            }
            awaitClose()
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
                Log.d(MAIN_TAG, "getProducts: $e")
            }

        }
    }

    override fun getCartProducts(listProductId: List<String>): Flow<List<ProductModel>> {
        return flow {
            try {
                val listProduct = mutableListOf<ProductModel>()
                for (id in listProductId) {
                    val querySnapshot =
                        productCollectionReference.whereEqualTo("id", id).get().await()
                    for (document in querySnapshot.documents) {
                        val product = document.toObject<ProductDto>()
                        if (product != null) {
                            listProduct.add(product.toProductModel())
                        }
                    }
                }
                emit(listProduct)
            } catch (e: Exception) {
                Log.d(MAIN_TAG, "getCartProducts: $e")
            }
        }
    }

    override fun getCurrentUserData(): Flow<ResultModel<UserModel>> {
        return flow {
            try {
                val loginData = getUserFromLogin()
                val querySnapshot =
                    userCollectionReference.whereEqualTo("id", loginData?.id).get().await()
                for (document in querySnapshot.documents) {
                    val userData = document.toObject(UserDto::class.java)
                    if (userData != null) {
                        saveUserToStorage(userData)
                        emit(ResultModel.Success(userData.toUserModel()))
                    } else {
                        emit(ResultModel.Error(Exception("User not exist")))
                    }
                }
            } catch (e: Exception) {
                Log.d(MAIN_TAG, "getCurrentData: $e")
            }
        }
    }

    override fun addProductToCart(cartProductId: String): Flow<ResultModel<UserModel>> {
        return flow {
            try {
                val userFromStorage = getUserDataFromStorage()
                val cartProductsFromStorage = userFromStorage?.cartProducts
                val newList: MutableList<String> = mutableListOf()
                for (id in cartProductsFromStorage!!) {
                    newList.add(id)
                }
                if (!newList.contains(cartProductId)) {
                    newList.add(cartProductId)
                } else {
                    emit(ResultModel.Error(Exception("This product already in cart")))
                    return@flow
                }
                val querySnapshot =
                    userCollectionReference.whereEqualTo("id", userFromStorage.id).get().await()
                        .firstOrNull()
                if (querySnapshot != null) {
                    userCollectionReference.document(querySnapshot.id).set(
                        getCartProductPersonMap(newList), SetOptions.merge()
                    )
                }
                val userDto = UserDto(
                    id = userFromStorage.id,
                    name = userFromStorage.name,
                    gender = userFromStorage.gender,
                    email = userFromStorage.email,
                    accountBalance = userFromStorage.accountBalance,
                    cartProducts = newList
                )
                saveUserToStorage(userDto)
                emit(ResultModel.Success(userDto.toUserModel()))
            } catch (e: Exception) {
                Log.d(MAIN_TAG, "addProduct: $e")
                emit(ResultModel.Error(e))
            }
        }
    }

    override fun changeUserData(userParam: UserParam): Flow<ResultModel<UserModel>> {
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
                            val userFromStorage = getUserDataFromStorage()
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
                            saveUserToStorage(userDto!!)
                            trySend(ResultModel.Success(userDto.toUserModel()))
                        } catch (e: Exception) {
                            trySend(ResultModel.Error(e))
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d(MAIN_TAG, "changeUserData: $e")
                trySend(ResultModel.Error(e))
            }
            awaitClose()
        }
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
                            val loginDto = LoginDto(loginParam.email, newPassword)
                            trySend(ResultModel.Success(loginDto.toLoginModel()))
                        } else {
                            updateTask.exception?.let {
                                val result = ResultModel.Error(it)
                                Log.d(MAIN_TAG, "changePassword: $it")
                                trySend(result)
                            }
                        }
                    }
                } else {
                    task.exception?.let {
                        val result = ResultModel.Error(it)
                        Log.d(MAIN_TAG, "changePassword: $it")
                        trySend(result)
                    }
                }
            }
            awaitClose()
        }
    }

    override fun deleteProductFromCart(cartProductId: String): Flow<ResultModel<UserModel>> {
        return flow {
            try {
                val userFromStorage = getUserDataFromStorage()
                val cartProductsFromStorage = userFromStorage?.cartProducts
                val newList = cartProductsFromStorage?.filter { string -> string != cartProductId }
                    ?: listOf()
                val querySnapshot =
                    userCollectionReference.whereEqualTo("id", userFromStorage?.id).get().await()
                        .firstOrNull()
                if (querySnapshot != null) {
                    userCollectionReference.document(querySnapshot.id).set(
                        getCartProductPersonMap(newList), SetOptions.merge()
                    )
                }
                Log.d(MAIN_TAG,"delete: $newList")
                val userDto = UserDto(
                    id = userFromStorage?.id,
                    name = userFromStorage?.name,
                    gender = userFromStorage?.gender,
                    email = userFromStorage?.email,
                    accountBalance = userFromStorage?.accountBalance,
                    cartProducts = newList
                )
                saveUserToStorage(userDto)
                emit(ResultModel.Success(userDto.toUserModel()))
            } catch (e: Exception) {
                Log.d(MAIN_TAG, "deleteProduct: $e")
                emit(ResultModel.Error(e))
            }
        }
    }

    override fun pay(cartProductIds: List<String>, newUserAccountBalance: String): Flow<ResultModel<UserModel>> {
        return flow {
            try {
                val userFromStorage = getUserDataFromStorage()
                val cartProductsFromStorage = userFromStorage?.cartProducts ?: listOf()
                val confirmationProductsFromStorage =
                    userFromStorage?.confirmationProducts ?: listOf()
                val newCartList = mutableListOf<String>()
                val newConfirmationList = mutableListOf<String>()
                for (payId in cartProductsFromStorage) {
                    if (cartProductIds.contains(payId)) {
                        newConfirmationList.add(payId)
                        continue
                    }
                    newCartList.add(payId)
                }
                for (purchasedId in confirmationProductsFromStorage) {
                    if (newConfirmationList.contains(purchasedId)) {
                        continue
                    }
                    newConfirmationList.add(purchasedId)
                }
                val querySnapshot =
                    userCollectionReference.whereEqualTo("id", userFromStorage?.id).get().await()
                        .firstOrNull()
                if (querySnapshot != null) {
                    userCollectionReference.document(querySnapshot.id).set(
                        getConfirmationProductByPayPersonMap(newCartList,newConfirmationList,newUserAccountBalance), SetOptions.merge()
                    )
                }
                val userDto = UserDto(
                    id = userFromStorage?.id,
                    name = userFromStorage?.name,
                    gender = userFromStorage?.gender,
                    email = userFromStorage?.email,
                    accountBalance = newUserAccountBalance,
                    cartProducts = newCartList,
                    confirmationProducts = newConfirmationList
                )
                saveUserToStorage(userDto)
                emit(ResultModel.Success(userDto.toUserModel()))
            } catch (e: Exception) {
                Log.d(MAIN_TAG, "Pay: $e")
                emit(ResultModel.Error(e))
            }
        }
    }

    override fun buy(
        cartProductId: String,
        productPrice: String,
    ): Flow<ResultModel<UserModel>> {
        return flow {
            try {
                val userFromStorage = getUserDataFromStorage()
                if(productPrice.toLong() > userFromStorage?.accountBalance?.toLong()!!) {
                    emit(ResultModel.Error(Exception("Your account is not enough to perform this service")))
                    return@flow
                }
                val newUserAccountBalance = userFromStorage.accountBalance.toLong() - productPrice.toLong()
                val confirmationProductsFromStorage = userFromStorage.confirmationProducts
                val newList : MutableList<String> = mutableListOf()
                for(id in confirmationProductsFromStorage) {
                    newList.add(id)
                }
                if(!newList.contains(cartProductId)) {
                    newList.add(cartProductId)
                } else {
                    emit(ResultModel.Error(Exception("This product already in cart")))
                    return@flow
                }
                val querySnapshot = userCollectionReference.whereEqualTo("id",userFromStorage.id).get().await().firstOrNull()
                if(querySnapshot != null) {
                    userCollectionReference.document(querySnapshot.id).set(
                        getConfirmationProductPersonMap(newList,newUserAccountBalance.toString()), SetOptions.merge()
                    )
                }
                val userDto = UserDto(
                    id = userFromStorage.id,
                    name = userFromStorage.name,
                    gender = userFromStorage.gender,
                    email = userFromStorage.email,
                    accountBalance = newUserAccountBalance.toString(),
                    confirmationProducts = newList
                )
                saveUserToStorage(userDto)
                emit(ResultModel.Success(userDto.toUserModel()))
            } catch (e: Exception) {
                Log.d(MAIN_TAG, "BUy: $e")
                emit(ResultModel.Error(e))
            }
        }
    }
}