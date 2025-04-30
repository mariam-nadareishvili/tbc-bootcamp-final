package com.tbc.bookli.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.tbc.bookli.core.ui.SnackbarManager
import com.tbc.bookli.core.ui.model.BookUi
import com.tbc.bookli.feature.intro.IntroductionRoute
import com.tbc.bookli.feature.splash.SplashRoute
import com.tbc.bookli.feature.bookshelf.BookShelfScreenRoute
import com.tbc.bookli.feature.bookshelf_details.BookShelfDetails
import com.tbc.bookli.presentation.component.BottomSheetHost
import com.tbc.bookli.presentation.component.rememberBottomSheetController
import com.tbc.bookli.presentation.component.BottomBar
import com.tbc.bookli.feature.details.BookDetailsRoute
import com.tbc.bookli.feature.home.HomeScreenRoute
import com.tbc.bookli.feature.login.LoginScreenRoute
import com.tbc.bookli.feature.profile.ProfileScreenRoute
import com.tbc.bookli.feature.read.ReadScreen
import com.tbc.bookli.feature.register.RegisterScreenRoute
import com.tbc.bookli.feature.review.ReviewScreenRoute
import com.tbc.bookli.feature.search.SearchScreenRoute
import com.tbc.bookli.presentation.component.BottomNavItem
import kotlinx.coroutines.flow.collectLatest
import kotlin.reflect.typeOf

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

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        SnackbarManager.messages.collectLatest { message ->
            snackbarHostState.showSnackbar(message.getString(context))
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            if (showBottomBar) {
                BottomBar(
                    items = bottomBarItems,
                    currentDestination = currentDestination,
                    onItemClick = { item ->
                        navController.navigate(item.screen) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = Splash,
                modifier = Modifier.fillMaxSize()
            ) {
                composable<Splash> {
                    SplashRoute(
                        onNavigateToIntro = {
                            navController.navigate(Introduction) {
                                navController.popBackStack()
                            }
                        },
                        onNavigateToLogin = {
                            navController.navigate(Login) {
                                navController.popBackStack()
                            }
                        },
                        onNavigateToHome = {
                            navController.navigate(HomeScreen) {
                                navController.popBackStack()
                            }
                        },
                    )
                }

                composable<Introduction> {
                    IntroductionRoute(
                        onNavigate = {
                            navController.navigate(Login) {
                                navController.popBackStack()
                            }
                        }
                    )
                }

                composable<Login> {
                    LoginScreenRoute(
                        onNavigateToHome = {
                            navController.navigate(HomeScreen) {
                                navController.popBackStack()
                            }
                        },
                        onNavigateToRegister = { navController.navigate(Register) }
                    )
                }

                composable<Register> {
                    RegisterScreenRoute(
                        onBackPress = { navController.navigateUp() },
                        onNavigateBackToLogin = { navController.navigateUp() }
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
                        onNavigateToReviewScreen = { book ->
                            navController.navigate(
                                ReviewScreen(bookUi = book)
                            )
                        },
                        onOpenBottomSheet = { sheetContent ->
                            bottomSheetController.openBottomSheet(sheetContent)
                        }
                    )
                }

                composable<Read> {
                    val args = it.toRoute<Read>()

                    ReadScreen(url = args.url)
                }

                composable<BookShelfScreen> {
                    BookShelfScreenRoute(
                        onNavigateToSavedBookScreen = { books ->
                            navController.navigate(SavedBooksScreen(books))
                        }
                    )
                }

                composable<SavedBooksScreen>(
                    typeMap = mapOf(typeOf<List<BookUi>>() to serializableType<List<BookUi>>())
                ) {
                    val args = it.toRoute<SavedBooksScreen>()

                    BookShelfDetails(
                        books = args.books,
                        onNavigateToDetails = { id ->
                            navController.navigate(BookDetails(bookId = id)) {
                                navController.popBackStack()
                            }
                        }
                    )
                }

                composable<ReviewScreen>(
                    typeMap = mapOf(typeOf<BookUi>() to serializableType<BookUi>())
                ) {
                    val args = it.toRoute<ReviewScreen>()
                    ReviewScreenRoute(
                        book = args.bookUi,
                        onBackPress = { navController.navigateUp() })
                }

                composable<SearchScreen> {
                    SearchScreenRoute(
                        onNavigateToBookDetails = { id -> navController.navigate(BookDetails(bookId = id)) }
                    )
                }

                composable<ProfileScreen> {
                    ProfileScreenRoute(
                        onNavigateToLogin = {
                            navController.navigate(Login) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }
            }

            BottomSheetHost(bottomSheetController = bottomSheetController)
        }
    }
}