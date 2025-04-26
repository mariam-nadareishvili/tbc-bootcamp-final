package com.example.android_bootcamp.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed class Resource<out T> {
    data class Loading(val isLoading: Boolean) : Resource<Nothing>()
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
}

fun <DTO, DOMAIN> Flow<Resource<DTO>>.mapResource(mapper: (DTO) -> DOMAIN): Flow<Resource<DOMAIN>> {
    return map { resource ->
        when (resource) {
            is Resource.Success -> {
                Resource.Success(data = resource.data?.let { mapper(it) })
            }

            is Resource.Error -> {
                Resource.Error(message = resource.message)
            }

            is Resource.Loading -> {
                Resource.Loading(isLoading = resource.isLoading)
            }
        }
    }
}
