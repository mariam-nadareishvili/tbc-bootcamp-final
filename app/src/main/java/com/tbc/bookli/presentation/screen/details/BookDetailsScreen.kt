package com.tbc.bookli.presentation.screen.details

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.Comment
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.tbc.bookli.R
import com.tbc.bookli.presentation.screen.AvatarType
import com.tbc.bookli.presentation.screen.bottom_sheet.BottomSheetContent
import com.tbc.bookli.presentation.screen.details.BookDetailsViewModel.BookDetailsUiEvent
import com.tbc.bookli.presentation.screen.home.RatingBarReadOnly
import com.tbc.bookli.presentation.screen.search.BookUi
import com.tbc.bookli.presentation.screen.search.ReviewUi
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BookDetailsRoute(
    id: String,
    viewModel: BookDetailsViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onNavigateToReadScreen: (String) -> Unit,
    onNavigateToBookDetails: (String) -> Unit,
    onNavigateToReviewScreen: (BookUi) -> Unit,
    onOpenBottomSheet: (BottomSheetContent) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(BookDetailsEvent.LoadBookDetails(id))

        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                is BookDetailsUiEvent.NavigateToBookDetails -> onNavigateToBookDetails(event.id)
                is BookDetailsUiEvent.NavigateToReadScreen -> onNavigateToReadScreen(event.url)
                is BookDetailsUiEvent.NavigateToReviewScreen -> onNavigateToReviewScreen(event.bookUi)
                BookDetailsUiEvent.OnBackPress -> onBackPress()
                is BookDetailsUiEvent.ShowError -> {}
            }
        }
    }

    BookDetailsScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onOpenBottomSheet = {
            onOpenBottomSheet(
                BottomSheetContent.SelectBookList { selectedList ->
                    viewModel.onEvent(BookDetailsEvent.UpdateBookStatus(selectedList))
                }
            )
        }
    )
}

@Composable
fun BookDetailsScreen(
    state: BookDetailsUiState,
    onEvent: (BookDetailsEvent) -> Unit,
    onOpenBottomSheet: () -> Unit
) {
    state.bookDetails?.run {
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
                        imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 16.dp)
                            .clickable { onEvent(BookDetailsEvent.OnBackPress) },
                        tint = Color.DarkGray
                    )
                    Icon(
                        painter = painterResource(
                            if (status != null) R.drawable.ic_full_heart else R.drawable.ic_empty_heart
                        ), contentDescription = null,
                        modifier = Modifier
                            .padding(20.dp)
                            .size(30.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                if (status != null) {
                                    onEvent(BookDetailsEvent.UpdateBookStatus(null))
                                } else {
                                    onOpenBottomSheet()
                                }
                            },
                        tint = Color.Red
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                AsyncImage(
                    model = imageUrl,
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

                Text(
                    text = author,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = title,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))
                RatingBarReadOnly(
                    rating = rating,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    starSize = 24.dp
                )
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Visibility,
                                contentDescription = "null",
                                tint = Color.Red,
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .size(16.dp)
                            )
                            Text(
                                text = stringResource(R.string.number_of_reads),
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        StatItem(value = readBy)
                    }
                    VerticalDivider(
                        modifier = Modifier
                            .height(32.dp)
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Reads",
                                tint = Color.Red,
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .size(16.dp)
                            )
                            Text(
                                text = stringResource(R.string.number_of_votes),
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        StatItem(value = votes)
                    }
                    VerticalDivider(
                        modifier = Modifier
                            .height(32.dp)
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Bookmarks,
                                contentDescription = "Reads",
                                tint = Color.Red,
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .size(16.dp)
                            )
                            Text(
                                text = stringResource(R.string.pages),
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        StatItem(value = pages.toString())
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                source?.let {
                    Button(
                        onClick = {
                            onEvent(BookDetailsEvent.NavigateToRead(source))
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
                                text = stringResource(R.string.button_start_reading),
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                LazyRow {
                    items(genres) { genre ->
                        ItemGenres(
                            genre = genre.name,
                            modifier = Modifier.padding(start = 20.dp)
                        )

                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = stringResource(R.string.introduction_of_book),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )
                ExpandableText(text = aboutBook)
                Text(
                    text = stringResource(R.string.information_about_author),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )
                ExpandableText(text = aboutBook)
                Review(
                    reviews = state.bookDetails.reviews,
                    onNavigateToReviewScreen = {
                        onEvent(BookDetailsEvent.NavigateToReview)
                    }
                )
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
                        text = stringResource(R.string.similar_books_title),
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
                                    .clickable { onEvent(BookDetailsEvent.NavigateToBookDetails(book.id)) }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
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
            .padding(horizontal = 20.dp, vertical = 2.dp)
    ) {
        Text(
            text = genre,
            fontSize = 14.sp,
            color = Color.White,
        )
    }
}


@Composable
fun Review(reviews: List<ReviewUi>, onNavigateToReviewScreen: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .align(Alignment.End)
                .clickable { onNavigateToReviewScreen() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Comment,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Leave your review",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        if (reviews.isNotEmpty()) {
            Text(
                text = stringResource(R.string.reviews),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            ExpandableCommentsBox(reviews = reviews)
        }
    }
}

@Composable
fun ExpandableCommentsBox(
    reviews: List<ReviewUi>,
    minimizedMaxComments: Int = 3
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .animateContentSize()
    ) {
        val visibleReviews = if (expanded) reviews else reviews.take(minimizedMaxComments)

        visibleReviews.forEachIndexed { index, review ->
            CommentItem(review = review)

            if (index != visibleReviews.lastIndex) {
                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
        }

        if (reviews.size > minimizedMaxComments) {
            Text(
                text = if (expanded) stringResource(R.string.show_less_information) else stringResource(
                    R.string.view_all_reviews
                ),
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
            text = if (expanded) stringResource(R.string.show_less_information) else stringResource(
                R.string.read_more
            ),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 10.dp)
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
fun CommentItem(review: ReviewUi) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(AvatarType.fromKey(review.avatar).drawableRes),
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
                    text = review.reviewerName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                RatingBarReadOnly(
                    rating = review.rating,
                    starSize = 14.dp,
                    showRatingNumber = false
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = review.comment,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = review.date,
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