package com.example.appshopping.data.dto

import com.example.appshopping.domain.model.LoginModel

class LoginDto(val id: String, val email: String) {

    fun toLoginModel(): LoginModel {
        return LoginModel(id = id, email = email)
    }
}