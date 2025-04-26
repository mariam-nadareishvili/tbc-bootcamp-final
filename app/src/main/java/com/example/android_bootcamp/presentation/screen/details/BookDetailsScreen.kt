package com.example.android_bootcamp.presentation.screen.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_bootcamp.R
import com.example.android_bootcamp.presentation.screen.home.BookItem
import com.example.android_bootcamp.presentation.screen.home.RatingBar

@Composable
fun BookDetailsRoute() {

}

@Composable
fun ItemGenres(genre: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Gray)
            .padding(horizontal = 16.dp, vertical = 8.dp) // Gives shape and space to text
    ) {
        Text(
            text = genre,
            fontSize = 14.sp,
            color = Color.White
        )
    }

}

@Composable
fun BookDetailsScreen() {
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier
                    .padding(20.dp),
                tint = colorResource(R.color.sky_blue)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(R.drawable.background_gradient),
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
                text = "Title",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Title",
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(10.dp))
            RatingBar(
                3.5,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                starSize = 24.dp
            )

            Spacer(modifier = Modifier.height(24.dp))
            val genres =
                listOf("History", "Fantasy", "Science")
            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) {
                items(genres) {
                    ItemGenres(genre = it)
                    Spacer(modifier = Modifier.width(8.dp)) // spacing between chips
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "About author",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.title_color),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Information about book's author",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Introduction",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.title_color),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Information about book",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )

            Review()

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
                    text = "title",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black // or colorResource(R.color.title_color)
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
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
//                items(books) {
//                    BookItem(title = it.first, author = it.second, ratingVisible = false, price =)
//                }
            }
            Spacer(modifier = Modifier.height(60.dp))


        }
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.sky_blue)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "Read now",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
                Icon(
                    painter = painterResource(R.drawable.ic_read),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
    }
}

@Composable
fun Review() {
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "Reviews",
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
    )
    val sampleComments = listOf(
        Triple("Nino", 4.5, "Beautifully written. I couldn't put it down."),
        Triple("Giorgi", 3.0, "Interesting story but a bit slow in the middle."),
        Triple("Tamar", 5.0, "Absolutely loved every part of it. Highly recommend!"),
        Triple("Luka", 4.2, "Great character development and engaging plot."),
        Triple("Salome", 2.5, "Not what I expected. The ending was disappointing."),
        Triple("Ana", 4.0, "Good read with a few unexpected twists."),
        Triple("Dato", 3.8, "Well-written but I wish it was longer."),
        Triple("Mariam", 5.0, "A masterpiece. Will definitely re-read this.")
    )
    sampleComments.forEach {
        CommentItem(name = it.first, rate = it.second, comment = it.third)
    }
}

@Preview(showBackground = true)
@Composable
fun BookDetailsPreview() {
    BookDetailsScreen()
}

@Composable
fun CommentItem(name: String, rate: Double, comment: String) {
    Spacer(modifier = Modifier.height(10.dp))
    Column(
        modifier = Modifier
            .border(1.dp, color = colorResource(R.color.title_color), RoundedCornerShape(20))
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_placeholder),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(20)),
                contentScale = ContentScale.Crop
            )
            Column() {
                Text(
                    text = name, fontSize = 14.sp, modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Rating", fontSize = 14.sp, modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                    RatingBar(rate, starSize = 18.dp)
                }
            }
        }
        Text(
            text = comment,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(vertical = 16.dp)
        )
        Text(
            text = "Apr 24, 2025",
            fontSize = 12.sp,
        )
    }
}


