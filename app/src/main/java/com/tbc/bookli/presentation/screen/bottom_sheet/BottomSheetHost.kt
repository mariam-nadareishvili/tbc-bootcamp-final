@file:OptIn(ExperimentalMaterial3Api::class)

package com.tbc.bookli.presentation.screen.bottom_sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun BottomSheetHost(
    bottomSheetController: BottomSheetController
) {
    val sheetState = bottomSheetController.sheetState
    val currentSheetContent = bottomSheetController.currentSheetContent
    val coroutineScope = rememberCoroutineScope()

    currentSheetContent?.let { sheetContent ->
        ModalBottomSheet(
            onDismissRequest = { bottomSheetController.hideBottomSheet() },
            sheetState = sheetState
        ) {
            when (sheetContent) {
                is BottomSheetContent.SelectBookList -> {
                    Column {
                        (1..10).forEach {
                            Text(
                                text = it.toString(), modifier = Modifier.clickable {
                                    sheetContent.onSelect(it.toString())
                                    coroutineScope.launch {
                                        bottomSheetController.hideBottomSheet()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
