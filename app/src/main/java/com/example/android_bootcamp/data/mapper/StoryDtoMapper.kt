package com.example.android_bootcamp.data.mapper

import com.example.android_bootcamp.data.remote.model.StoryDto
import com.example.android_bootcamp.domain.model.Story

fun StoryDto.toDomain(): Story {
    return Story(id = id, quote = quote, imageUrl = imageUrl)
}