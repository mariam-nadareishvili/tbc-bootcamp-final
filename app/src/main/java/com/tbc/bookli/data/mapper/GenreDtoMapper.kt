package com.tbc.bookli.data.mapper

import com.tbc.bookli.data.remote.model.GenreDto
import com.tbc.bookli.domain.model.Genre

fun GenreDto.toDomain(): Genre {
    return Genre(id = id, name = name)
}