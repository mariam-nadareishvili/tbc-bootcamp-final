package com.tbc.bookli.core.data.mapper

import com.tbc.bookli.core.data.remote.model.GenreDto
import com.tbc.bookli.core.domain.model.Genre

fun GenreDto.toDomain(): Genre {
    return Genre(id = id, name = name)
}