package com.example.appshopping.data.dto

import com.example.appshopping.domain.model.main.ProductModel

class ProductDto(
    val name: String,
    val price: String,
    val image: String,
    val origin: String,
) {
    fun toProductModel(): ProductModel {
        return ProductModel(
            name = name,
            price = price,
            image = image,
            origin = origin
        )
    }
}