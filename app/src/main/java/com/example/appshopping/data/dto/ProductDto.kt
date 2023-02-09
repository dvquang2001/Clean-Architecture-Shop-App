package com.example.appshopping.data.dto

import com.example.appshopping.domain.model.main.ProductModel

class ProductDto(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val price: String? = null,
    val images: List<String> = listOf(),
    val origin: String? = null,
    val type: String? = null
) {
    fun toProductModel(): ProductModel {
        return ProductModel(
            id = id!!,
            name = name!!,
            description = description!!,
            price = price!!,
            images = images,
            origin = origin!!,
            type = type!!
        )
    }

    override fun toString(): String {
        return "id: $id, name = $name,desc = $description, price = $price, image = $images, origin = $origin, type = $type"
    }
}