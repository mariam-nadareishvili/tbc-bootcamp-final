package com.tbc.bookli.feature.bookshelf

import com.tbc.bookli.core.domain.model.BookStatus

sealed class BookShelfEvent {
    data object LoadBooks : BookShelfEvent()
    data class NavigateToBookShelfDetails(val status: BookStatus) : BookShelfEvent()
}
