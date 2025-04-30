package com.tbc.bookli.feature.bookshelf

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.tbc.bookli.core.common.R
import com.tbc.bookli.core.domain.model.BookStatus
import com.tbc.bookli.core.ui.components.BookliLoader
import com.tbc.bookli.core.ui.components.RotatingCirclePieChart
import com.tbc.bookli.feature.bookshelf.BookShelfViewModel.BookShelfUiEvent
import com.tbc.bookli.core.ui.model.BookUi
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BookShelfScreenRoute(
    onNavigateToSavedBookScreen: (List<BookUi>) -> Unit,
    viewModel: BookShelfViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(BookShelfEvent.LoadBooks)

        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                is BookShelfUiEvent.NavigateToSavedBookScreen -> onNavigateToSavedBookScreen(event.books)
            }
        }
    }

    BookShelfScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun BookShelfScreen(
    state: BookShelfUiState,
    onEvent: (BookShelfEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Library",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column {
            ReadingListItem(
                books = state.favoriteBooks,
                title = BookStatus.Favorites.value,
                onNavigateToSavedBookScreen = {
                    onEvent(
                        BookShelfEvent.NavigateToBookShelfDetails(status = BookStatus.Favorites)
                    )
                }
            )
            Spacer(Modifier.height(8.dp))
            ReadingListItem(
                books = state.readingBooks,
                title = BookStatus.Reading.value,
                onNavigateToSavedBookScreen = {
                    onEvent(
                        BookShelfEvent.NavigateToBookShelfDetails(BookStatus.Reading)
                    )
                }
            )
            if (state.favoriteBooks.isNotEmpty() || state.readingBooks.isNotEmpty()) {
                Spacer(Modifier.height(40.dp))
                Text(
                    text = "Statistics",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(8.dp))
                RotatingCirclePieChart(books = state.favoriteBooks + state.readingBooks)
            }
        }
    }

    if (state.isLoading) {
        BookliLoader()
    }
}

@Composable
fun ReadingListItem(
    books: List<BookUi>,
    title: String,
    onNavigateToSavedBookScreen: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                if (books.isNotEmpty()) {
                    onNavigateToSavedBookScreen()
                }
            }
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(90.dp)
                .height(120.dp)
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(x = 16.dp, y = 16.dp)
                    .background(Color.LightGray, shape = MaterialTheme.shapes.medium)
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(x = 8.dp, y = 8.dp)
                    .background(Color.DarkGray, shape = MaterialTheme.shapes.medium)
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
            ) {
                AsyncImage(
                    model = books.firstOrNull()?.imageUrl ?: R.drawable.ic_placeholder,
                    contentDescription = null,
                    modifier = Modifier.clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.width(30.dp))

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${books.size} books",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookShelfScreenPreview() {
    BookShelfScreen(onEvent = {}, state = BookShelfUiState())
}