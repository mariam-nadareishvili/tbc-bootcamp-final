package com.example.android_bootcamp.presentation.screen.search

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_bootcamp.R
import com.example.android_bootcamp.presentation.screen.details.ItemGenres
import com.example.android_bootcamp.presentation.screen.home.BookItem

@Composable
fun SearchScreenRoute() {
}

@Composable
fun SearchScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .padding(20.dp),
            tint = colorResource(R.color.sky_blue)
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search here...", modifier = Modifier.padding(top = 2.dp)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 30.dp)
                .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(30)),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),

            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_seach),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 20.dp, end = 10.dp)
                )
            }
        )
        val genres =
            listOf("History", "Fantasy", "Science", "Romance", "Science", "Science", "Science")
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 30.dp)
        ) {
            items(genres) {
                ItemGenres(genre = it)
                Spacer(modifier = Modifier.width(8.dp)) // spacing between chips
            }
        }
        val books = listOf(
            "1984" to "George Orwell",
            "Brave New World" to "Aldous Huxley",
            "Fahrenheit 451" to "Ray Bradbury",
            "The Hobbit" to "J.R.R. Tolkien",
            "Fahrenheit 451" to "Ray Bradbury",
            "The Hobbit" to "J.R.R. Tolkien",
            "Fahrenheit 451" to "Ray Bradbury",
            "The Hobbit" to "J.R.R. Tolkien"
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
//            items(books) {
//                BookItem(author = it.first, title = it.second,)
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}