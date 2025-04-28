package com.tbc.bookli.data.mapper

import com.tbc.bookli.data.remote.model.LocationDto
import com.tbc.bookli.domain.model.Location

fun LocationDto.toDomain(): Location {
    return Location(latitude = latitude, longitude = longitude, title = title, address = address)
}