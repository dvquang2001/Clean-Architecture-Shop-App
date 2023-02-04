package com.example.appshopping.domain.model

interface ResultModel<out T> {

    data class Success<T>(val result: T): ResultModel<T>
    data class Error(val t: Throwable): ResultModel<Nothing>
    class Loading<T>: ResultModel<T>
}