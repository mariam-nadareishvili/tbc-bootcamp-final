package com.example.android_bootcamp.presentation.screen.bookshelf

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_bootcamp.R

@Composable
fun BookShelfScreenRoute() {

}

@Composable
fun BookShelfScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp),
                tint = colorResource(R.color.sky_blue),

                )
            Text(
                text = "My Favorites",
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(start = 10.dp)

            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        val bookTitles = listOf(
            "The Great Gatsby",
            "To Kill a Mockingbird",
            "1984",
            "Pride and Prejudice",
            "The Catcher in the Rye",
            "Moby-Dick",
            "War and Peace",
            "The Lord of the Rings",
            "Harry Potter and the Sorcerer's Stone",
            "The Hobbit"
        )

        Spacer(modifier = Modifier.height(30.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(bookTitles) { book ->
                BookShelfItem()
            }
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MenuBook,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp),
                tint = colorResource(R.color.sky_blue),

                )
            Text(
                text = "Reading ...",
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(start = 10.dp)

            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(30.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(bookTitles) { book ->
                BookShelfItem()
            }
        }

    }
}

@Composable
fun BookShelfItem() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.background_gradient),
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .height(150.dp)
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(10)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "title",
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookShelfScreenPreview() {
    BookShelfScreen()
}
