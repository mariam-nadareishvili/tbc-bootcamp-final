package com.tbc.bookli.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GenreItem(genre: String, modifier: Modifier = Modifier, selected: Boolean = false) {
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