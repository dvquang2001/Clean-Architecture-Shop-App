package com.example.appshopping.domain.usecase.main.get_user

data class UserParam(
    val id: String,
    val name: String,
    val email: String,
    val gender: String,
    val accountBalance: String
)