package com.example.android_bootcamp.presentation.mapper

import com.example.android_bootcamp.domain.model.Story
import com.example.android_bootcamp.presentation.model.StoryUi

fun Story.toPresentation(): StoryUi {
    return StoryUi(id = id, quote = quote, imageUrl = imageUrl)
}