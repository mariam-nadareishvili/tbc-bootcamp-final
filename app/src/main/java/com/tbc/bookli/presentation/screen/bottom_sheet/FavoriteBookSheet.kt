package com.tbc.bookli.presentation.screen.bottom_sheet

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.runtime.Composable

import androidx.compose.ui.draw.clip

@Composable
fun FavoriteBookSheet(
    onDone: (String) -> Unit,
    initialSelection: BookListType = BookListType.FAVORITES
) {
    var selectedList by remember { mutableStateOf(initialSelection) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Add To",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Done",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        onDone(selectedList.displayName)
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            BookListType.entries.forEach { type ->
                FavoriteItem(
                    icon = when (type) {
                        BookListType.FAVORITES -> Icons.Default.Star
                        BookListType.READING -> Icons.Default.CollectionsBookmark
                    },
                    title = type.displayName,
                    isSelected = type == selectedList,
                    onClick = { selectedList = type }
                )
            }
        }
    }
}

@Composable
fun FavoriteItem(
    icon: ImageVector,
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.08f) else Color.Transparent,
        label = "BackgroundColorAnimation"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

enum class BookListType(val displayName: String) {
    FAVORITES("Favorites"),
    READING("Reading")
}


@Preview(showBackground = true)
@Composable
fun BottomSheetItemsPreview() {
    MaterialTheme {
        FavoriteBookSheet(
            onDone = {}
        )
    }
}