package com.example.android_bootcamp.presentation.screen.search

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.android_bootcamp.R
import com.example.android_bootcamp.presentation.screen.details.ItemGenres
import com.example.android_bootcamp.presentation.screen.home.BookItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchScreenRoute(
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateToBookDetails: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                is SearchViewModel.SearchUiEvent.NavigateToBookDetail -> onNavigateToBookDetails(
                    event.id
                )

                is SearchViewModel.SearchUiEvent.ShowError -> {}
            }
        }
    }
    SearchScreen(
        state = state,
        onNavigateToBookDetails = viewModel::navigateToBookDetails,
        onSearchQueryChange = viewModel::search,
        onSelectGenre = viewModel::searchByGenre
    )
}

@Composable
fun SearchScreen(
    state: SearchUiState,
    onNavigateToBookDetails: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSelectGenre: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        TextField(
            value = state.searchQuery,
            onValueChange = { newValue -> onSearchQueryChange(newValue) },
            placeholder = { Text("Search here...", modifier = Modifier.padding(top = 2.dp)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 30.dp)
                .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(30)),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),

            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_seach),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 20.dp, end = 10.dp)
                )
            }
        )

        if (state.searchQuery.isBlank()) {
            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 30.dp)
            ) {
                items(state.genres) { genre ->
                    ItemGenres(
                        genre = genre.name,
                        selected = genre.name==state.selectedGenre,
                        modifier = Modifier.clickable { onSelectGenre(genre.name) })
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(state.searchBook) { book ->
                BookItem(
                    imageUrl = book.imageUrl,
                    title = book.title,
                    author = book.author,
                    ratingAndPriceVisible = true,
                    rating = book.rating,
                    averagePrice = book.averagePrice,
                    modifier = Modifier
                        .clickable { onNavigateToBookDetails(book.id) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        state = SearchUiState(),
        onNavigateToBookDetails = {},
        onSearchQueryChange = {},
        onSelectGenre = {}
    )
}