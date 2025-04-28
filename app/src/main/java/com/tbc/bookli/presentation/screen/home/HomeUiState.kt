package com.tbc.bookli.presentation.screen.home

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class HomeUiState(
    val isLoading: Boolean = false,
    val storyList: List<StoryUi> = emptyList(),
    val feedBookList: Flow<PagingData<FeedBookUi>> = flow {}
)