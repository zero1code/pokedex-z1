package com.z1.pokedex.core.datasource

import com.z1.pokedex.core.datasource.model.ResultData
import com.z1.pokedex.core.network.model.NetworkResult
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseDataSource {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultData<T> {
        return try {
            val response = apiCall.invoke()
            ResultData.Success(response)
        } catch (e: HttpException) {
            e.printStackTrace()
            ResultData.Error("${e.message()}: ${e.code()}")
        } catch (e: Exception) {
            e.printStackTrace()
            ResultData.Error(e.message)
        }
    }
}