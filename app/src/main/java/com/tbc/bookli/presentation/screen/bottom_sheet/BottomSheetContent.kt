package com.tbc.bookli.presentation.screen.bottom_sheet

sealed class BottomSheetContent {
    data class SelectBookList(val onSelect: (String) -> Unit) : BottomSheetContent()
}