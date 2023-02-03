package com.example.appshopping.data.dto

import com.example.appshopping.domain.model.RegisterModel

class RegisterDto (
    val email: String,
    val id: String
) {
    fun toRegisterModel(): RegisterModel {
        return RegisterModel(id = id, email = email)
    }
}