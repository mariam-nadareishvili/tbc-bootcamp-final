package com.tbc.bookli.presentation.screen.bottom_sheet

import com.tbc.bookli.domain.model.BookStatus

sealed class BottomSheetContent {
    data class SelectBookList(val onSelect: (BookStatus) -> Unit) : BottomSheetContent()
}