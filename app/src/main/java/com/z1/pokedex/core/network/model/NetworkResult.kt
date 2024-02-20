package com.z1.pokedex.core.network.model

sealed class NetworkResult<T> {
    class Success<T>(val data: T): NetworkResult<T>()
    class Error<T>(val message: String): NetworkResult<T>()
    class Exception<T>(val e: Throwable): NetworkResult<T>()
}