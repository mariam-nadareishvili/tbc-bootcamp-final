@file:OptIn(ExperimentalMaterial3Api::class)

package com.tbc.bookli.presentation.component

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.tbc.bookli.core.ui.model.BottomSheetContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberBottomSheetController(): BottomSheetController {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    return remember {
        BottomSheetController(coroutineScope, sheetState)
    }
}

class BottomSheetController(
    private val coroutineScope: CoroutineScope,
    val sheetState: SheetState
) {
    var currentSheetContent by mutableStateOf<BottomSheetContent?>(null)
        private set

    fun openBottomSheet(content: BottomSheetContent) {
        currentSheetContent = content
        coroutineScope.launch {
            sheetState.show()
        }
    }

    fun hideBottomSheet() {
        coroutineScope.launch {
            sheetState.hide()
            currentSheetContent = null
        }
    }
}