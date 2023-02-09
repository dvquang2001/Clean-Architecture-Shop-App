package com.example.appshopping.data.dto

import com.example.appshopping.domain.model.main.UserModel

class UserDto(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val gender: String? = null,
    val accountBalance: String? = null
) {
    fun toUserModel(): UserModel {
        return UserModel(id!!,name!!,email ?: "",password?:"",gender!!,accountBalance?: "")
    }

    override fun toString(): String {
        return "id = $id,name = $name,email = $email,password = $password,gender = $gender,account = $accountBalance"
    }
}