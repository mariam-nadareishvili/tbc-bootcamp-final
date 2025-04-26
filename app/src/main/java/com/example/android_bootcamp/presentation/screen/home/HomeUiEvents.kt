package com.example.android_bootcamp.presentation.screen.home


sealed class HomeUiEvents {
    data class ShowError(val message: String) : HomeUiEvents()
    data class NavigateToBookDetails(val id :String) : HomeUiEvents()
}