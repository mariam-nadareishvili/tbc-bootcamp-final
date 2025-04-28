package com.tbc.bookli.presentation.screen.introduction

import androidx.annotation.RawRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.tbc.bookli.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun IntroductionScreen(
    slides: List<IntroductionSlide> = introductionSlides,
    onFinished: () -> Unit
) {
    val pagerState = rememberPagerState(
        pageCount = { slides.size }
    )
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (pagerState.currentPage != slides.lastIndex) {
                    TextButton(onClick = { onFinished() }) {
                        Text(stringResource(R.string.skip))
                    }
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                val slide = slides[page]

                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LottieAnimation(
                            animationResId = slide.animationResId,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        IntroductionTypingText(
                            text = stringResource(slide.title),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(initialAlpha = 0.3f),
                        ) {
                            Text(
                                text = stringResource(slide.description),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                                lineHeight = 26.sp,
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.5.sp,
                                modifier = Modifier
                                    .padding(horizontal = 24.dp)
                                    .padding(top = 8.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                pageCount = slides.size,
                modifier = Modifier.padding(16.dp)
            )

            Button(
                onClick = {
                    if (pagerState.currentPage == slides.lastIndex) {
                        onFinished()
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(
                        if (pagerState.currentPage == slides.lastIndex) {
                            R.string.get_started
                        } else {
                            R.string.next
                        }
                    )
                )
            }
        }
    }
}

@Composable
internal fun IntroductionTypingText(
    text: String,
    modifier: Modifier = Modifier
) {
    var visibleText by remember { mutableStateOf("") }
    val totalDuration = 1500L
    val charInterval = totalDuration / text.length

    LaunchedEffect(text) {
        text.forEachIndexed { index, _ ->
            visibleText = text.take(index + 1)
            delay(charInterval)
        }
    }

    Text(
        text = visibleText,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    )
}

@Composable
internal fun LottieAnimation(@RawRes animationResId: Int, modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationResId))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenContentPreview() {
    IntroductionScreen(
        onFinished = {}
    )
}