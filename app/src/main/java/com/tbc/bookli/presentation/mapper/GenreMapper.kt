package com.tbc.bookli.presentation.mapper

import com.tbc.bookli.domain.model.Genre
import com.tbc.bookli.presentation.screen.search.GenreUi

fun Genre.toPresentation(): GenreUi {
    return GenreUi(id = id, name = name)
}