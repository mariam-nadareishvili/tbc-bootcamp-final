package com.example.android_bootcamp.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.android_bootcamp.presentation.model.FeedBookUi
import com.example.android_bootcamp.presentation.model.StoryUi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToBookDetails: (String) -> Unit
) {
    val homeUiState by viewModel.homeState.collectAsStateWithLifecycle()
    val feedBookPagingItems = homeUiState.feedBookList.collectAsLazyPagingItems()
    val pagerState = rememberPagerState(pageCount = { homeUiState.storyList.size })

    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % homeUiState.storyList.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.homeUiEvents.collectLatest { event ->
            when (event) {
                is HomeUiEvents.NavigateToBookDetails -> onNavigateToBookDetails(event.id)
                is HomeUiEvents.ShowError -> {}
            }
        }
    }

    HomeScreen(
        state = homeUiState,
        feedBookPaging = feedBookPagingItems,
        pagerState = pagerState,
        onNavigateToBookDetails = viewModel::navigateToBookDetails
    )
}


@Composable
fun HomeScreen(
    state: HomeUiState,
    feedBookPaging: LazyPagingItems<FeedBookUi>,
    pagerState: PagerState,
    onNavigateToBookDetails: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10))
                .height(150.dp)
                .background(Color.Gray) // Background applies *under* pager
        ) { page ->
            StoryItem(
                story = state.storyList[page],
                modifier = Modifier
                    .clickable {
                        onNavigateToBookDetails(state.storyList[page].id)
                    }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(state.storyList.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (isSelected) 10.dp else 8.dp) // Correct use of size here
                        .clip(RoundedCornerShape(50))
                        .background(if (isSelected) Color.Black else Color.LightGray)
                )
            }
        }


        Spacer(modifier = Modifier.height(30.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(feedBookPaging.itemCount) { index ->
                val feedBook = feedBookPaging[index]
                feedBook?.let { it ->
                    BookItem(
                        modifier = Modifier
                            .width(60.dp)
                            .clickable { onNavigateToBookDetails(it.id) },
                        uiState = it
                    )
                }
            }
        }
    }
}

@Composable
fun StoryItem(story: StoryUi, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = story.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = story.quote,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 50.dp, start = 10.dp, end = 10.dp)


        )
    }
}

@Composable
fun BookItem(
    uiState: FeedBookUi,
    modifier: Modifier = Modifier,
    ratingVisible: Boolean = true,
    starSize: Dp = 14.dp,
    showRatingNumber: Boolean = false,
    author: String? = null
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp, vertical = 10.dp),
    ) {
        AsyncImage(
            model = uiState.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(256.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = uiState.title,
            fontSize = 18.sp,
            fontWeight = Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (ratingVisible) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RatingBar(
                    rating = uiState.rating,
                    showRatingNumber = showRatingNumber,

                    starSize = starSize
                )
                Text(
                    text = "$${uiState.averagePrice}",
                    fontSize = 16.sp,
                    fontWeight = Bold,
                    modifier = Modifier.padding(end = 5.dp)
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    val state = HomeUiState()
//    val pagerState = rememberPagerState(pageCount = { state.storyList.size })
//    HomeScreen(
//        state = state,
//        feedBookPaging = state.feedBookList.collectAsLazyPagingItems(),
//        pagerState = pagerState,
//        onNavigateToBookDetails = {}
//    )
//}
