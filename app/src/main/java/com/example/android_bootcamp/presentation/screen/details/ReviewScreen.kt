package com.example.android_bootcamp.presentation.screen.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_bootcamp.R
import com.example.android_bootcamp.presentation.screen.home.RatingBar

@Composable
fun ReviewScreenRoute() {

}

@Composable
fun ReviewScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp),
            tint = colorResource(R.color.sky_blue)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Text(
                    text = "Title",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 30.dp)

                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Title",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 30.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))
                val genres =
                    listOf("History", "Fantasy")
                LazyRow(
                    modifier = Modifier
                        .padding(start = 30.dp)
                ) {
                    items(genres) {
                        ItemGenres(genre = it)
                        Spacer(modifier = Modifier.width(8.dp)) // spacing between chips
                    }
                }
            }

            Image(
                painter = painterResource(R.drawable.background_gradient),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .width(150.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(5)),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Rate this book:",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 30.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            RatingBar(
                rating = 3.5,
                showRatingNumber = false,
                starSize = 30.dp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Add a review::",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 30.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(start = 30.dp, end = 30.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                ), shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = "Submit",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewScreenPreview() {
    ReviewScreen()
}