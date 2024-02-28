package com.z1.pokedex.core.network.datasource

import com.z1.pokedex.core.network.model.NetworkResult
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseDataSource {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        return try {
            val response = apiCall.invoke()
            response.takeIf { it.isSuccessful }?.body()
                ?.let { NetworkResult.Success(it) }
                ?: NetworkResult.Error(response.errorBody().toString())
        } catch (e: HttpException) {
            e.printStackTrace()
            NetworkResult.Error("${e.message()}: ${e.code()}")
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResult.Error(e.message)
        }
    }
}