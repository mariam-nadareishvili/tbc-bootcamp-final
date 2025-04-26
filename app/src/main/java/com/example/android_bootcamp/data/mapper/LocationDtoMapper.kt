package com.example.android_bootcamp.data.mapper

import com.example.android_bootcamp.data.remote.model.LocationDto
import com.example.android_bootcamp.domain.model.Location

fun LocationDto.toDomain(): Location {
    return Location(latitude = latitude, longitude = longitude, title = title, address = address)
}