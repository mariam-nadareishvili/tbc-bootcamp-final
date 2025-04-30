@file:OptIn(ExperimentalMaterial3Api::class)

package com.tbc.bookli.presentation.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import com.tbc.bookli.core.ui.components.FavoriteBookSheet
import com.tbc.bookli.core.ui.model.BottomSheetContent

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
