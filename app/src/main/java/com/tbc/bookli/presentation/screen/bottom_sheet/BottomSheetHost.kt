@file:OptIn(ExperimentalMaterial3Api::class)

package com.tbc.bookli.presentation.screen.bottom_sheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable

@Composable
fun BottomSheetHost(
    bottomSheetController: BottomSheetController
) {
    val sheetState = bottomSheetController.sheetState
    val currentSheetContent = bottomSheetController.currentSheetContent

    currentSheetContent?.let { sheetContent ->
        ModalBottomSheet(
            onDismissRequest = { bottomSheetController.hideBottomSheet() },
            sheetState = sheetState
        ) {
            when (sheetContent) {
                is BottomSheetContent.SelectBookList -> {
                    FavoriteBookSheet(
                        onDone = {
                            sheetContent.onSelect.invoke(it)
                            bottomSheetController.hideBottomSheet()
                        }
                    )
                }
            }
        }
    }
}
