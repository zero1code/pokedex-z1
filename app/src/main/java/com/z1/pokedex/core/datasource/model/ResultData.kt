package com.z1.pokedex.core.datasource.model

sealed class ResultData<T> {
    class Success<T>(val data: T): ResultData<T>()
    class Error<T>(val message: String?): ResultData<T>()
}