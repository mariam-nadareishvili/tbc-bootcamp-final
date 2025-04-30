package com.tbc.bookli.core.ui.model

import com.tbc.bookli.core.domain.model.BookStatus

sealed class BottomSheetContent {
    data class SelectBookList(val onSelect: (BookStatus) -> Unit) : BottomSheetContent()
}