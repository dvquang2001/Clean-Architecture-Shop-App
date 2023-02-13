package com.example.appshopping.data.dto

import com.example.appshopping.domain.model.main.UserModel

class UserDto(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val gender: String? = null,
    val accountBalance: String? = null,
    val cartProducts: List<String> = listOf(),
    val confirmationProducts: List<String> = listOf(),
    val deliveryProducts: List<String> = listOf()
) {
    fun toUserModel(): UserModel {
        return UserModel(
            id!!,
            name!!,
            email ?: "",
            gender!!,
            accountBalance ?: "",
            cartProducts,
            confirmationProducts,
            deliveryProducts
        )
    }
    override fun toString(): String {
        return "id = $id,name = $name,email = $email,gender = $gender,account = $accountBalance, productId: $cartProducts"
    }
}