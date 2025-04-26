package com.example.android_bootcamp.data.remote.sevice

import com.example.android_bootcamp.data.remote.model.LocationDto
import retrofit2.Response
import retrofit2.http.GET

interface LocationApiService {
    @GET("locations")
    suspend fun getLocations(): Response<List<LocationDto>>
}