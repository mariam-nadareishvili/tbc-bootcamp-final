package com.tbc.bookli.presentation.screen.bookshelf

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.bookli.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BookShelfScreenRoute(
    onNavigateToSavedBookScreen: () -> Unit,
    viewModel: BookShelfViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                BookShelfViewModel.BookShelfUiEvent.NavigateToSavedBookScreen -> onNavigateToSavedBookScreen()
                BookShelfViewModel.BookShelfUiEvent.OnBackPress -> {}
                is BookShelfViewModel.BookShelfUiEvent.ShowError -> {}
            }

        }
    }
    BookShelfScreen(
        state = state,
        onNavigateToSavedBookScreen = viewModel::navigateToSavedBookScreen

    )
}

@Composable
fun BookShelfScreen(
    state: BookShelfUiState,
    onNavigateToSavedBookScreen: () -> Unit,
) {
    val readingLists = listOf(
        ReadingList("Favourite", 5, image = R.drawable.ic_man),
        ReadingList("Reading", 3, image = R.drawable.ic_google_map),
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Library",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(readingLists) { readingList ->
                Column {
                    ReadingListItem(
                        readingList,
                        onNavigateToSavedBookScreen = onNavigateToSavedBookScreen
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun ReadingListItem(readingList: ReadingList, onNavigateToSavedBookScreen: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToSavedBookScreen() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(90.dp)
                .height(120.dp) // More vertical to match book style
        ) {
            // Back rectangle 2
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(x = 16.dp, y = 16.dp)
                    .background(Color.LightGray, shape = MaterialTheme.shapes.medium)
            )

            // Back rectangle 1
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(x = 8.dp, y = 8.dp)
                    .background(Color.DarkGray, shape = MaterialTheme.shapes.medium)
            )

            // Main "book cover" image
            Box(
                modifier = Modifier
                    .matchParentSize()
            ) {
                // Here, you can load the real image with AsyncImage or Image(painterResource())
                Image(
                    modifier = Modifier.clip(MaterialTheme.shapes.medium),
                    painter = painterResource(readingList.image),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )

            }
        }

        Spacer(modifier = Modifier.width(30.dp))

        Column {
            Text(
                text = readingList.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${readingList.storyCount} books",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

data class ReadingList(
    val title: String,
    val storyCount: Int,
    val image: Int
)

@Preview(showBackground = true)
@Composable
fun BookShelfScreenPreview() {
    BookShelfScreen(onNavigateToSavedBookScreen = {}, state = BookShelfUiState())
}