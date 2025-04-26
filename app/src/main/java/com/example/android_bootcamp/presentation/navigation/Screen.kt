package com.example.android_bootcamp.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object Login

    @Serializable
    object Register

    @Serializable
    object Home

    @Serializable
    object BookShelf

    @Serializable
    object Search

    @Serializable
    object Profile

    @Serializable
    data class BookDetails(val bookId: String)

    @Serializable
    data class ReadBook(val url: String)

}