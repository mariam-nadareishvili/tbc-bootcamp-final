package com.example.android_bootcamp.presentation.screen.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_bootcamp.R
import kotlin.math.floor


@Composable
fun RatingBar(
    rating: Double,
    modifier: Modifier = Modifier,
    stars: Int = 5,
    starSize: Dp = 14.dp,
    starColor: Color = colorResource(R.color.rating_color),
    backgroundColor: Color = colorResource(R.color.rating_color),
    spacing: Dp = 4.dp,
    showRatingNumber: Boolean = true,
    minVisibleFraction: Float = 0.3f // minimum width shown for any partial star

) {
    val fullStars = floor(rating).toInt()
    val partial = (rating - fullStars).toFloat()
    val visualPartial = partial.coerceAtLeast(minVisibleFraction.takeIf { partial > 0 } ?: 0f)
    val hasPartial = partial > 0
    val emptyStars = stars - fullStars - if (hasPartial) 1 else 0

    Row(
        modifier = modifier.padding(start = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Full stars
        repeat(fullStars) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = starColor,
                modifier = Modifier.size(starSize)
            )
        }


        // Partial star with minimum visual width
        if (hasPartial) {
            Box(modifier = Modifier.size(starSize)) {
                Icon(
                    imageVector = Icons.Outlined.StarOutline,
                    contentDescription = null,
                    tint = backgroundColor,
                    modifier = Modifier.matchParentSize()
                )
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null,
                    tint = starColor,
                    modifier = Modifier
                        .matchParentSize()
                        .drawWithContent {
                            clipRect(right = size.width * visualPartial) {
                                this@drawWithContent.drawContent()
                            }
                        }
                )
            }
        }

        // Empty stars
        repeat(emptyStars) {
            Icon(
                imageVector = Icons.Outlined.StarOutline,
                contentDescription = null,
                tint = backgroundColor,
                modifier = Modifier.size(starSize)
            )
        }
        if (showRatingNumber) {
            Text(
                text = "$rating",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 4.dp)
            )
        }
    }
}


@Preview
@Composable
fun RatingPreview() {
    RatingBar(rating = 3.5)
}

@Preview
@Composable
fun TenStarsRatingPreview() {
    RatingBar(stars = 10, rating = 8.5)
}

@Preview
@Composable
fun RatingPreviewFull() {
    RatingBar(rating = 5.0)
}

@Preview
@Composable
fun RatingPreviewDisabled() {
    RatingBar(rating = 2.5, backgroundColor = Color.Yellow)
}