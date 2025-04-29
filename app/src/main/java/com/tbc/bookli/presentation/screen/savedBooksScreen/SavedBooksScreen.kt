@file:OptIn(ExperimentalLayoutApi::class)

package com.tbc.bookli.presentation.screen.savedBooksScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tbc.bookli.R

@Composable
fun SavedBooksScreen() {
    val stories = listOf(
        StoryItem(
            title = "A and D",
            views = "45,7M",
            likes = "820K",
            chapters = "52",
            description = "Nerdy Dakota Evans makes the biggest mistake of her life by falling in love with...",
            tags = listOf("teenfiction", "clique")
        ),
        StoryItem(
            title = "Pregnant By The Alpha",
            views = "2,49M",
            likes = "63,1K",
            chapters = "56",
            description = "\"You are mine to kiss,\" Damian kisses me and runs his hands through my hair...",
            tags = listOf("dark", "omega", "fantasy")
        ),
        StoryItem(
            title = "Professional Restraint",
            views = "1,51M",
            likes = "37,5K",
            chapters = "62",
            description = "When a young data scientist unexpectedly steals the heart of a high...",
            tags = listOf("hotencount", "workplace")
        )
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.LightGray)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_man),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .blur(24.dp)
            )

            // Semi-transparent overlay (optional for better readability)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )

            Row(
                modifier = Modifier.fillMaxHeight().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .width(70.dp)
                        .height(100.dp)
                ) {
                    Image(painter = painterResource(R.drawable.ic_google_map), contentDescription = null)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "test",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Text(
                        text = "4 stories",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(stories) { story ->
                StoryItemView(story)
            }
        }
    }
}

@Composable
fun StoryItemView(story: StoryItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.ic_hide_view),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(72.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = story.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    IconText(Icons.Default.RemoveRedEye, story.views)
                    IconText(Icons.Default.Star, story.likes)
                    IconText(Icons.Default.List, story.chapters)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = story.description,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    story.tags.forEach { tag ->
                        AssistChip(
                            onClick = { /* handle tag */ },
                            label = { Text(tag) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IconText(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

// Small helper model
data class StoryItem(
    val title: String,
    val views: String,
    val likes: String,
    val chapters: String,
    val description: String,
    val tags: List<String>
)

@Preview(showBackground = true)
@Composable
fun SavedBooksScreenPreview() {
    SavedBooksScreen()
}