@file:OptIn(ExperimentalMaterial3Api::class)

package com.tbc.bookli.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.tbc.bookli.presentation.screen.bookshelf.BookShelfScreenRoute
import com.tbc.bookli.presentation.screen.bottom_sheet.BottomSheetHost
import com.tbc.bookli.presentation.screen.bottom_sheet.rememberBottomSheetController
import com.tbc.bookli.presentation.screen.details.BookDetailsRoute
import com.tbc.bookli.presentation.screen.review.ReviewScreen
import com.tbc.bookli.presentation.screen.home.HomeScreenRoute
import com.tbc.bookli.presentation.screen.introduction.IntroductionScreen
import com.tbc.bookli.presentation.screen.login.LoginScreenRoute
import com.tbc.bookli.presentation.screen.profile.ProfileScreenRoute
import com.tbc.bookli.presentation.screen.read.ReadScreen
import com.tbc.bookli.presentation.screen.register.RegisterScreenRoute
import com.tbc.bookli.presentation.screen.review.ReviewScreenRoute
import com.tbc.bookli.presentation.screen.review.ReviewUiState
import com.tbc.bookli.presentation.screen.savedBooksScreen.SavedBooksScreen
import com.tbc.bookli.presentation.screen.search.SearchScreenRoute

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

    val bottomSheetController = rememberBottomSheetController()

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

                                    is BottomNavItem.BookShelf -> Icon(
                                        Icons.AutoMirrored.Filled.MenuBook,
                                        contentDescription = null
                                    )

                                    is BottomNavItem.Profile -> Icon(
                                        Icons.Default.Person,
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
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = HomeScreen,
                modifier = Modifier.fillMaxSize()
            ) {
                composable<Introduction> {
                    IntroductionScreen {}
                }

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
                        onBackPress = { navController.navigateUp() },
                        onNavigateToReviewScreen = { navController.navigate(ReviewScreen) }
                    )
                }

                composable<Read> {
                    val args = it.toRoute<Read>()

                    ReadScreen(url = args.url)
                }

                composable<BookShelfScreen> {
                    BookShelfScreenRoute(onNavigateToSavedBookScreen = {
                        navController.navigate(
                            SavedBooksScreen
                        )
                    })

                }
                composable<SavedBooksScreen> {
                    SavedBooksScreen()

                }

                composable<ReviewScreen> {
                    ReviewScreenRoute(onBackPress = { navController.navigateUp() }
                    )

                }
                composable<SearchScreen> {
                    SearchScreenRoute(
                        onNavigateToBookDetails = { id -> navController.navigate(BookDetails(bookId = id)) }
                    )
                }
                composable<ProfileScreen> {
                    ProfileScreenRoute(
                        onNavigateToLogin = {
                            navController.popBackStack(
                                route = navController.graph.startDestinationRoute
                                    ?: return@ProfileScreenRoute,
                                inclusive = true
                            )
                            navController.navigate(Login)
                        },
                        onOpenBottomSheet = { sheetContent ->
                            bottomSheetController.openBottomSheet(sheetContent)
                        }
                    )
                }
            }

            BottomSheetHost(bottomSheetController = bottomSheetController)
        }
    }
}