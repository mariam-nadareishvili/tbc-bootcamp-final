package com.tbc.bookli.presentation.screen.search

sealed class SearchEvent {
    data class QueryChanged(val query: String) : SearchEvent()
    data class GenreSelected(val genre: String) : SearchEvent()
    data class BookClicked(val id: String) : SearchEvent()
}