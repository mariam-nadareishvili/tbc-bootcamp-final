package com.tbc.bookli.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tbc.bookli.core.ui.RatingColor
import kotlin.math.floor


@Composable
fun RatingBarReadOnly(
    rating: Double,
    modifier: Modifier = Modifier,
    stars: Int = 5,
    starSize: Dp = 14.dp,
    starColor: Color = RatingColor,
    backgroundColor: Color = RatingColor,
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
        repeat(fullStars) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = starColor,
                modifier = Modifier.size(starSize)
            )
        }


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
                text = rating.toString(),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun RatingBarInput(
    selectedRating: Int,
    onRatingSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    stars: Int = 5,
    starSize: Dp = 28.dp,
    selectedColor: Color = RatingColor,
    unselectedColor: Color = Color.LightGray,
    spacing: Dp = 4.dp
) {
    Row(
        modifier = modifier.padding(start = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (index in 1..stars) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = if (index <= selectedRating) selectedColor else unselectedColor,
                modifier = Modifier
                    .size(starSize)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onRatingSelected(index) }
            )
        }
    }
}



@Preview
@Composable
fun RatingPreview() {
    RatingBarReadOnly(rating = 3.5)
}

@Preview
@Composable
fun TenStarsRatingPreview() {
    RatingBarReadOnly(stars = 10, rating = 8.5)
}

@Preview
@Composable
fun RatingPreviewDisabled() {
    RatingBarReadOnly(rating = 2.5, backgroundColor = Color.Yellow)
}