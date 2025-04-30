package com.tbc.bookli.feature.home

sealed class HomeEvent {
    data object LoadFeedBooks : HomeEvent()
    data object LoadStories : HomeEvent()
    data class BookClicked(val id: String) : HomeEvent()
}
