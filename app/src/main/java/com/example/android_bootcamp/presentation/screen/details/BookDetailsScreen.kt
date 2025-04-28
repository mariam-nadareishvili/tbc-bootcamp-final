package com.example.android_bootcamp.presentation.screen.details

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.android_bootcamp.R
import com.example.android_bootcamp.presentation.screen.details.BookDetailsViewModel.BookDetailsUiEvent
import com.example.android_bootcamp.presentation.screen.home.BookItem
import com.example.android_bootcamp.presentation.screen.home.RatingBar
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BookDetailsRoute(
    id: String,
    onBackPress: () -> Unit,
    viewModel: BookDetailsViewModel = hiltViewModel(),
    onNavigateToReadScreen: (String) -> Unit,
    onNavigateToBookDetails: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getBookDetails(id = id)

        viewModel.uiEvents.collectLatest {
            when (it) {
                is BookDetailsUiEvent.NavigateToBookDetails -> onNavigateToBookDetails(it.id)

                is BookDetailsUiEvent.NavigateToReadScreen -> onNavigateToReadScreen(it.url)

                is BookDetailsUiEvent.ShowError -> {}

                is BookDetailsUiEvent.OnBackPress -> onBackPress()
                else -> {}
            }
        }

    }
    BookDetailsScreen(
        state = state,
        onNavigateToReadScreen = viewModel::navigateToReadScreen,
        onBackPress = viewModel::navigateWithBackIcon,
        onNavigateToBookDetails = viewModel::navigateToBookDetails
    )
}

@Composable
fun BookDetailsScreen(
    state: BookDetailsUiState,
    onNavigateToReadScreen: (String?) -> Unit,
    onBackPress: () -> Unit,
    onNavigateToBookDetails: (String) -> Unit
) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(20.dp)
                        .clickable { onBackPress() },
                    tint = colorResource(R.color.sky_blue)
                )
                Icon(
                    painter = painterResource(R.drawable.ic_empty_heart),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(20.dp)
                        .clickable { },
                    tint = Color.Red
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            AsyncImage(
                model = state.bookDetails?.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .width(150.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(5))
                    .align(Alignment.CenterHorizontally),

                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(10.dp))

            state.bookDetails?.let {
                Text(
                    text = it.author,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            state.bookDetails?.let {
                Text(
                    text = it.title,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            state.bookDetails?.let {
                RatingBar(
                    rating = state.bookDetails.rating,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    starSize = 24.dp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row() {
                        Icon(
                            imageVector = Icons.Default.Visibility, // 👁️ <-- your icon
                            contentDescription = "Reads",
                            tint = Color.Gray,
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(16.dp)
                        )
                        Text(
                            text = "Read",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    StatItem(value = "2,42m")
                }
                VerticalDivider(
                    modifier = Modifier
                        .height(32.dp)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row() {
                        Icon(
                            imageVector = Icons.Default.Star, // 👁️ <-- your icon
                            contentDescription = "Reads",
                            tint = Color.Gray,
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(16.dp)
                        )
                        Text(
                            text = "Votes",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    StatItem(value = "58,5k")
                }
                VerticalDivider(
                    modifier = Modifier
                        .height(32.dp)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row() {
                        Icon(
                            imageVector = Icons.Default.Bookmarks, // 👁️ <-- your icon
                            contentDescription = "Reads",
                            tint = Color.Gray,
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(16.dp)
                        )
                        Text(
                            text = "Pages",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    StatItem(value = "81")
                }
            }
            Spacer(modifier = Modifier.height(30.dp))

            if (state.bookDetails?.source != null) {
                Button(
                    onClick = {
                        onNavigateToReadScreen(state.bookDetails.source)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_read),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .size(25.dp),
                            tint = MaterialTheme.colorScheme.background
                        )
                        Text(
                            text = "Start reading",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            LazyRow {
                state.bookDetails?.let {
                    items(it.genres) { genre ->
                        ItemGenres(genre = genre.name, modifier = Modifier.padding(start = 20.dp))

                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Introduction",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            state.bookDetails?.let {
                ExpandableText(text = it.aboutBook)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "About author",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            state.bookDetails?.let {
                ExpandableText(text = it.aboutBook)
            }

            Review()

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp),
                    color = Color.Gray
                )
                Text(
                    text = "Book reminds me of",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                        .height(3.dp),
                    color = Color.Gray
                )
            }
            LazyRow(
                modifier = Modifier
                    .padding(vertical = 20.dp)
            ) {
                state.similarBooks?.let {
                    items(it) { book ->
                        SimilarBookItem(
                            imageUrl = book.imageUrl,
                            title = book.title,
                            modifier = Modifier
                                .clickable { onNavigateToBookDetails(book.id) }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }


    }
}


@Composable
private fun StatItem(value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Composable
fun ItemGenres(genre: String, modifier: Modifier = Modifier, selected: Boolean = false) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(25.dp))
            .background(if (selected) Color.Black else Color.Gray)
            .padding(horizontal = 20.dp, vertical = 2.dp) // Gives shape and space to text
    ) {
        Text(
            text = genre,
            fontSize = 14.sp,
            color = Color.White,
        )
    }
}


@Composable
fun Review() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Reviews",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        val sampleComments = listOf(
            Triple("Nino", 4.5, "Beautifully written. I couldn't put it down."),
            Triple("Giorgi", 3.0, "Interesting story but a bit slow in the middle."),
            Triple("Tamar", 5.0, "Absolutely loved every part of it. Highly recommend!"),
            Triple("Luka", 4.2, "Great character development and engaging plot."),
            Triple("Salome", 2.5, "Not what I expected. The ending was disappointing."),
            Triple("Ana", 4.0, "Good read with a few unexpected twists."),
            Triple("Dato", 3.8, "Well-written but I wish it was longer."),
            Triple("Mariam", 5.0, "A masterpiece. Will definitely re-read this.")
        )

        ExpandableCommentsBox(sampleComments)
    }
}
@Composable
fun ExpandableCommentsBox(
    comments: List<Triple<String, Double, String>>,
    minimizedMaxComments: Int = 3
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 12.dp)
            .animateContentSize()
    ) {
        val visibleComments = if (expanded) comments else comments.take(minimizedMaxComments)

        visibleComments.forEachIndexed { index, (name, rate, comment) ->
            CommentItem(name, rate, comment)

            if (index != visibleComments.lastIndex) {
                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
        }

        if (comments.size > minimizedMaxComments) {
            Text(
                text = if (expanded) "Show Less ▲" else "View All Reviews ▼",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { expanded = !expanded }
                    .padding(vertical = 12.dp)
            )
        }
    }
}
@Composable
fun ExpandableText(
    text: String,
    minimizedMaxLines: Int = 3
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .animateContentSize()
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = if (expanded) "Show Less ▲" else "Read More ▼",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.End)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    expanded = !expanded
                }
                .padding(vertical = 6.dp)
        )
    }
}

@Composable
fun CommentItem(name: String, rate: Double, comment: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_placeholder),
            contentDescription = "Profile",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.2f))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                RatingBar(
                    rating = rate,
                    starSize = 14.dp,
                    showRatingNumber = false
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = comment,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Apr 24, 2025",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun SimilarBookItem(imageUrl: String, title: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(200.dp)
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(10)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = modifier.height(10.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookDetailsPreview() {
    BookDetailsScreen(state = BookDetailsUiState(),
        onNavigateToReadScreen = {},
        onBackPress = {},
        onNavigateToBookDetails = {}
    )
}
