package com.tbc.bookli.core.ui.mapper

import com.tbc.bookli.core.domain.model.Story
import com.tbc.bookli.core.ui.model.StoryUi

fun Story.toPresentation(): StoryUi {
    return StoryUi(id = id, quote = quote, imageUrl = imageUrl)
}