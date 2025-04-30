package com.tbc.bookli.core.ui.mapper

import com.tbc.bookli.core.domain.model.Genre
import com.tbc.bookli.core.ui.model.GenreUi

fun Genre.toPresentation(): GenreUi {
    return GenreUi(id = id, name = name)
}