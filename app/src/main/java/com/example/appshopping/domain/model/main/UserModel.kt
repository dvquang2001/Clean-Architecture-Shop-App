package com.example.appshopping.domain.model.main

data class UserModel(
    val id: String,
    val name: String,
    val email: String,
    val gender: String,
    val accountBalance: String,
    val cartProducts: List<String>,
    val confirmationProducts: List<String>,
    val deliveryProducts: List<String>
): java.io.Serializable