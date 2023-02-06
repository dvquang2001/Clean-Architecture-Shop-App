package com.example.appshopping.data.data_source

import com.example.appshopping.R
import com.example.appshopping.domain.model.main.ProductModel

fun getListImage() = listOf(
    R.drawable.ss22,
    R.drawable.ip14,
    R.drawable.nike
)

fun fakeListProduct() = listOf(
    ProductModel("SamSung s22","2100$",R.drawable.ss22,"Korea"),
    ProductModel("Iphone 14","2000$",R.drawable.ip14,"USA"),
    ProductModel("Nike","1200$",R.drawable.nike,"USA")
)