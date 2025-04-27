package com.example.android_bootcamp.presentation.navigation

import BottomBar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.android_bootcamp.presentation.screen.home.HomeScreenRoute
import com.example.android_bootcamp.presentation.screen.login.LoginScreenRoute
import com.example.android_bootcamp.presentation.screen.register.RegisterScreenRoute
import com.example.android_bootcamp.presentation.navigation.Screen.*
import com.example.android_bootcamp.presentation.screen.details.BookDetailsRoute
import com.example.android_bootcamp.presentation.screen.read.ReadScreen
import com.example.android_bootcamp.presentation.screen.search.SearchScreenRoute

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val shouldShowBottomBar = remember(currentRoute) {
        currentRoute?.let { route ->
            route in listOf(Home, Search, BookShelf, Profile)
        } ?: false
    }

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
                    navController.navigate(Read(url = url))
                },
                onNavigateToBookDetails = { id -> navController.navigate(BookDetails(bookId = id)) },
                onBackPress = { navController.navigateUp() }
            )
        }
        composable<Read> {
            val args = it.toRoute<Read>()

            ReadScreen(url = args.url)
        }

        composable<BookShelf> {

        }

        composable<Search> {
            SearchScreenRoute(onBackPress = { navController.navigateUp() },
                onNavigateToBookDetails = { id -> navController.navigate(BookDetails(bookId = id)) })
        }

        composable<Profile> {

        }
    }
    if (shouldShowBottomBar) {
        BottomBar(navController = navController)
    }
}