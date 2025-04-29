package com.tbc.bookli.presentation.screen.review

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tbc.bookli.presentation.screen.details.ItemGenres
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.tbc.bookli.presentation.screen.home.RatingBarInput
import com.tbc.bookli.presentation.screen.search.BookUi


@Composable
fun ReviewScreenRoute(
    book: BookUi,
    onBackPress: () -> Unit,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(ReviewScreenEvent.SaveBook(book = book))

        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                is ReviewViewModel.ReviewUiEvent.OnBackPress -> onBackPress()
            }
        }
    }
    ReviewScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun ReviewScreen(
    state: ReviewUiState,
    onEvent: (ReviewScreenEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .clickable { onEvent(ReviewScreenEvent.BackPress) },
            tint = Color.DarkGray
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = state.bookUi?.title ?: "",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = state.bookUi?.author ?: "",
                    fontSize = 20.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow {
                    items(state.bookUi?.genres ?: emptyList()) {
                        ItemGenres(genre = it.name)
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                }
            }

            AsyncImage(
                modifier = Modifier
                    .size(width = 100.dp, height = 140.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = state.bookUi?.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Your rating",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(30.dp))

            RatingBarInput(
                selectedRating = state.rating,
                onRatingSelected = { onEvent.invoke(ReviewScreenEvent.UpdateRating(rating = it)) },
                starSize = 48.dp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "Write a review",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = state.review,
                onValueChange = {
                    onEvent(ReviewScreenEvent.UpdateReview(it))
                },
                placeholder = { Text("Share your thoughts...", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.LightGray
                )

            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    onEvent(ReviewScreenEvent.Submit)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary

                )
            ) {
                Text(text = "Submit", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}