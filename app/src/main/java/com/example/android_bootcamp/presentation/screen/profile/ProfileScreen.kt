package com.example.android_bootcamp.presentation.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.android_bootcamp.R


@Composable
fun ProfileScreenRoute(
    viewmodel: ProfileViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit
) {
    val state by viewmodel.state.collectAsStateWithLifecycle()


    ProfileScreen(
        state = state,
        onToggleTheme = viewmodel::onToggleTheme,
        onNavigateToLogin = viewmodel::navigateToLogin
    )
}

@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onToggleTheme: (Boolean) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Image(
            painter = painterResource(R.drawable.ic_placeholder),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(70.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Name Surname",
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Email",
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(80.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_web),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(30.dp)
            )
            Text(
                text = "Language",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(end = 110.dp)
            )
            Image(
                painter = painterResource(R.drawable.geo),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_dark_mode),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(40.dp)
            )
            Text(
                text = "Dark Mode",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(end = 100.dp)
            )
            Switch(
                modifier = Modifier
                    .padding(end = 10.dp),
                checked = state.isDarkMode,
                onCheckedChange = { onToggleTheme(it) }
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        HorizontalDivider(modifier = Modifier.fillMaxWidth(1f))
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToLogin() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_log_out),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(50.dp),
                tint = Color.Red
            )
            Text(
                text = "Log Out",
                fontSize = 20.sp,
                color = Color.Red,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(state = ProfileUiState(), onToggleTheme = {}, onNavigateToLogin = {}
    )
}