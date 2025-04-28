package com.tbc.bookli.data.remote.repository

import com.tbc.bookli.common.Resource
import com.tbc.bookli.data.remote.sevice.LocationApiService
import com.tbc.bookli.data.remote.model.LocationDto
import com.tbc.bookli.domain.repository.LocationRepository
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