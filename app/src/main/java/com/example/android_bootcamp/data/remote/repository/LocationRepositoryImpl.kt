package com.example.android_bootcamp.data.remote.repository

import com.example.android_bootcamp.common.Resource
import com.example.android_bootcamp.data.remote.sevice.LocationApiService
import com.example.android_bootcamp.data.remote.model.LocationDto
import com.example.android_bootcamp.data.remote.safeApiCall
import com.example.android_bootcamp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepositoryImpl @Inject constructor(
    private val api: LocationApiService
) : LocationRepository {

    override suspend fun getPlaces(): Flow<Resource<List<LocationDto>>> = flow {
//        emit(Resource.Loading)

//        safeApiCall(
//            apiCall = { api.getLocations() },
//            onSuccess = { response -> emit(Resource.Success(response)) },
//            onError = { errorMessage -> emit(Resource.Error(errorMessage)) }
//        )
    }
}