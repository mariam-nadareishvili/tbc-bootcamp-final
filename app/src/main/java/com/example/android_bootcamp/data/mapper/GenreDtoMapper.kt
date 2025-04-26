package com.example.android_bootcamp.data.mapper

import com.example.android_bootcamp.data.remote.model.GenreDto
import com.example.android_bootcamp.domain.model.Genre

fun GenreDto.toDomain(): Genre {
    return Genre(id = id, name = name)
}