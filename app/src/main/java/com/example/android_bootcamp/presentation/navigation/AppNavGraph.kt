package com.example.android_bootcamp.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.android_bootcamp.presentation.screen.home.HomeScreenRoute
import com.example.android_bootcamp.presentation.screen.login.LoginScreenRoute
import com.example.android_bootcamp.presentation.screen.register.RegisterScreenRoute
import com.example.android_bootcamp.presentation.navigation.Screen.*
import com.example.android_bootcamp.presentation.screen.details.BookDetailsRoute

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Login> {
            LoginScreenRoute(
                onNavigateToHome = { navController.navigate(Home) },
                onNavigateToRegister = { navController.navigate(Register) },
                snackbarHostState = SnackbarHostState()
            )
        }

        composable<Register> {
            RegisterScreenRoute(
                onBackPress = { navController.navigateUp() },
                onNavigateBackToLogin = { navController.navigateUp() },
                snackbarHostState = SnackbarHostState()

            )
        }

        composable<Home> {
            HomeScreenRoute(
                onNavigateToBookDetails = { bookId ->
                    navController.navigate(BookDetails(bookId = bookId))
                }
            )
        }

        composable<BookDetails> {
            val args = it.toRoute<BookDetails>()

            BookDetailsRoute(
                id = args.bookId,
                onNavigateToReadScreen = { url ->
                    navController.navigate(ReadBook(url = url))
                },
                onNavigateToBookDetails = { id -> navController.navigate(BookDetails(bookId = id)) },
            )
        }
        composable<ReadBook> {
            val args = it.toRoute<ReadBook>()

        }

        composable<BookShelf> {

        }

        composable<Search> {

        }

        composable<Profile> {

        }
    }
}