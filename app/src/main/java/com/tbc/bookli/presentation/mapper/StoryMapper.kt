package com.tbc.bookli.presentation.mapper

import com.tbc.bookli.domain.model.Story
import com.tbc.bookli.presentation.screen.home.StoryUi

fun Story.toPresentation(): StoryUi {
    return StoryUi(id = id, quote = quote, imageUrl = imageUrl)
}