package com.example.appshopping.domain.model.main

data class UserModel(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val gender: String,
    val accountBalance: String,
    val productId: List<String>
)