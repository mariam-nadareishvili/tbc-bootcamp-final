package com.example.android_bootcamp.presentation.mapper

import com.example.android_bootcamp.data.remote.model.GenreDto
import com.example.android_bootcamp.domain.model.Genre
import com.example.android_bootcamp.presentation.screen.search.GenreUi

fun Genre.toPresentation(): GenreUi {
    return GenreUi(id = id, name = name)
}