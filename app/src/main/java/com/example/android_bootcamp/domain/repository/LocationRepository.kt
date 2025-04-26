package com.example.android_bootcamp.domain.repository

import com.example.android_bootcamp.common.Resource
import com.example.android_bootcamp.data.remote.model.LocationDto
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getPlaces(): Flow<Resource<List<LocationDto>>>
}