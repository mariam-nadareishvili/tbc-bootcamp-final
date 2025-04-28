package com.example.android_bootcamp.presentation.navigation

import BookDetails
import BookShelfScreen
import HomeScreen
import Login
import ProfileScreen
import Read
import Register
import SearchScreen
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.android_bootcamp.data.local.store.PreferenceStore
import com.example.android_bootcamp.domain.useCase.ClearPreferencesUseCase
import com.example.android_bootcamp.domain.useCase.GetAppLanguageUseCase
import com.example.android_bootcamp.domain.useCase.GetDarkModeUseCase
import com.example.android_bootcamp.domain.useCase.SetDarkModeUseCase
import com.example.android_bootcamp.domain.useCase.UpdateLanguageUseCase
import com.example.android_bootcamp.presentation.screen.details.BookDetailsRoute
import com.example.android_bootcamp.presentation.screen.home.HomeScreenRoute
import com.example.android_bootcamp.presentation.screen.login.LoginScreenRoute
import com.example.android_bootcamp.presentation.screen.profile.ProfileScreenRoute
import com.example.android_bootcamp.presentation.screen.profile.ProfileViewModel
import com.example.android_bootcamp.presentation.screen.read.ReadScreen
import com.example.android_bootcamp.presentation.screen.register.RegisterScreenRoute
import com.example.android_bootcamp.presentation.screen.search.SearchScreenRoute

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    val bottomBarItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.BookShelf,
        BottomNavItem.Profile
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    val showBottomBar = when (currentDestination?.route) {
        HomeScreen::class.qualifiedName,
        SearchScreen::class.qualifiedName,
        BookShelfScreen::class.qualifiedName,
        ProfileScreen::class.qualifiedName -> true

        else -> false
    }

    val snackbarHostState = remember { SnackbarHostState() } // ✅ create only ONCE

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomBarItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentDestination?.route == item.screen::class.qualifiedName,
                            onClick = {
                                navController.navigate(item.screen) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            label = { Text(text = stringResource(id = item.labelId)) },
                            icon = {
                                when (item) {
                                    is BottomNavItem.Home -> Icon(
                                        Icons.Default.Home,
                                        contentDescription = null
                                    )

                                    is BottomNavItem.Search -> Icon(
                                        Icons.Default.Search,
                                        contentDescription = null
                                    )

                                    is BottomNavItem.Profile -> Icon(
                                        Icons.Default.Person,
                                        contentDescription = null
                                    )

                                    is BottomNavItem.BookShelf -> Icon(
                                        Icons.Default.Book,
                                        contentDescription = null
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = HomeScreen,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<Login> {
                LoginScreenRoute(
                    onNavigateToHome = { navController.navigate(HomeScreen) },
                    onNavigateToRegister = { navController.navigate(Register) },
                    snackbarHostState = snackbarHostState // ✅ pass the SAME
                )
            }

            composable<Register> {
                RegisterScreenRoute(
                    onBackPress = { navController.navigateUp() },
                    onNavigateBackToLogin = { navController.navigateUp() },
                    snackbarHostState = snackbarHostState // ✅ pass the SAME
                )
            }

            composable<HomeScreen> {
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

            composable<BookShelfScreen> { }
            composable<SearchScreen> {
                SearchScreenRoute(
                    onNavigateToBookDetails = { id -> navController.navigate(BookDetails(bookId = id)) }
                )
            }
            composable<ProfileScreen> {
                ProfileScreenRoute(
                    onNavigateToLogin = {}
                )
            }
        }
    }
}
