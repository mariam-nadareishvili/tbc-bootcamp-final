package com.tbc.bookli.core.ui

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object SnackbarManager {
    private val _messages = MutableSharedFlow<UiText>(extraBufferCapacity = 5)
    val messages: SharedFlow<UiText> = _messages

    suspend fun showMessage(message: UiText) {
        _messages.emit(message)
    }
}