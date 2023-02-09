package com.example.appshopping.domain.model.main

data class ProductModel(
    val id: String,
    val name: String,
    val description: String,
    val price: String,
    val images: List<String>,
    val origin: String,
    val type: String
)