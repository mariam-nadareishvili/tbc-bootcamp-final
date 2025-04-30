package com.tbc.bookli.core.data.remote


import com.tbc.bookli.core.domain.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Flow<Resource<T>> = flow {
    emit(Resource.Loading(true))

    try {
        val response = apiCall()

        if (response.isSuccessful) {
            response.body()?.let {
                emit(Resource.Success(it))
            } ?: emit(Resource.Error("No data available"))
        } else {
            emit(Resource.Error(response.errorBody()?.string() ?: "General Error"))
        }
    } catch (e: Exception) {
        emit(Resource.Error(e.message ?: "Unknown Error"))
    } finally {
        emit(Resource.Loading(false))
    }
}

fun <T> safeFirebaseCall(
    block: suspend () -> T
): Flow<Resource<T>> = flow {
    emit(Resource.Loading(true))
    try {
        val result = block()
        emit(Resource.Success(result))
    } catch (e: Exception) {
        emit(Resource.Error(e.message ?: "Unknown error"))
    } finally {
        emit(Resource.Loading(false))
    }
}