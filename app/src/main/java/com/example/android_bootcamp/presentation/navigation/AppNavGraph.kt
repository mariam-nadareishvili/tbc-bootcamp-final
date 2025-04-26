package com.example.android_bootcamp.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android_bootcamp.presentation.screen.home.HomeScreenRoute
import com.example.android_bootcamp.presentation.screen.login.LoginScreenRoute
import com.example.android_bootcamp.presentation.screen.register.RegisterScreenRoute

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("login") {
            LoginScreenRoute(
                onNavigateToHome = { navController.navigate("home") },
                onNavigateToRegister = { navController.navigate("register") },
                snackbarHostState = SnackbarHostState()
            )
        }

        composable("register") {
            RegisterScreenRoute(
                onBackPress = { navController.navigateUp() },
                onNavigateBackToLogin = { navController.navigateUp() },
                snackbarHostState = SnackbarHostState()

            )
        }

        composable("home") {
            HomeScreenRoute(onNavigateToBookDetails = {})
        }
    }
}