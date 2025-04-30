package com.tbc.bookli.core.ui.components

import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tbc.bookli.core.common.R.raw

@Composable
fun BookliLottie(@RawRes animationResId: Int, modifier: Modifier = Modifier) {
    BookliLottieInternal(
        animationResId = animationResId,
        modifier = modifier
    )
}

@Composable
fun BookliLoader(modifier: Modifier = Modifier, @RawRes animationResId: Int = raw.loader1, ) {
    BookliLottieInternal(
        animationResId = animationResId,
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.5f))
            .fillMaxSize()
    )
}

@Composable
private fun BookliLottieInternal(
    @RawRes animationResId: Int,
    modifier: Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationResId))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Box(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {},
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress }
        )
    }
}