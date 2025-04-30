package com.tbc.bookli.core.data.mapper

import com.tbc.bookli.core.data.remote.model.StoryDto
import com.tbc.bookli.core.domain.model.Story

fun StoryDto.toDomain(): Story {
    return Story(id = id, quote = quote, imageUrl = imageUrl)
}