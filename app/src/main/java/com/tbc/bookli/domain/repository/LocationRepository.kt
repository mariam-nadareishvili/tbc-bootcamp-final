package com.tbc.bookli.domain.repository

import com.tbc.bookli.common.Resource
import com.tbc.bookli.data.remote.model.LocationDto
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getPlaces(): Flow<Resource<List<LocationDto>>>
}