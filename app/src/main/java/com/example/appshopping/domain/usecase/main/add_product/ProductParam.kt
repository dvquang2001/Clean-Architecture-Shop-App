package com.example.appshopping.domain.usecase.main.add_product

data class ProductParam(
    val name: String,
    val price: String,
    val image: Int,
    val origin: String
)