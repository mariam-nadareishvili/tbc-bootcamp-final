package com.tbc.bookli.data.mapper

import com.tbc.bookli.data.remote.model.StoryDto
import com.tbc.bookli.domain.model.Story

fun StoryDto.toDomain(): Story {
    return Story(id = id, quote = quote, imageUrl = imageUrl)
}