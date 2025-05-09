package com.tbc.bookli.feature.home

import androidx.paging.PagingData
import com.tbc.bookli.core.ui.model.FeedBookUi
import com.tbc.bookli.core.ui.model.StoryUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class HomeUiState(
    val isLoading: Boolean = false,
    val storyList: List<StoryUi> = emptyList(),
    val feedBookList: Flow<PagingData<FeedBookUi>> = flow {}
)