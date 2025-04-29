package com.tbc.bookli.presentation.screen.bookshelf

import com.tbc.bookli.domain.model.BookStatus

sealed class BookShelfEvent {
    data object LoadBooks : BookShelfEvent()
    data class NavigateToBookShelfDetails(val status: BookStatus) : BookShelfEvent()
}
