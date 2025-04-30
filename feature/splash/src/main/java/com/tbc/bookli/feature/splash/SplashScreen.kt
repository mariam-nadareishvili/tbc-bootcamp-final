package com.tbc.bookli.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tbc.bookli.core.common.R
import com.tbc.bookli.feature.splash.SplashViewModel.SplashUiEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashRoute(
    onNavigateToIntro: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collectLatest {
            when(it) {
                SplashUiEvent.NavigateToIntro -> onNavigateToIntro()
                SplashUiEvent.NavigateToLogin -> onNavigateToLogin()
                SplashUiEvent.NavigateToHome -> onNavigateToHome()
            }
        }
    }

    SplashScreen()
}


@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(170.dp)
                .clip(CircleShape),
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = null
        )
    }
}