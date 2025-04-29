package com.tbc.bookli.presentation.navigation

import com.tbc.bookli.presentation.screen.search.BookUi
import kotlinx.serialization.Serializable


sealed interface Screen

@Serializable
data object Splash : Screen

@Serializable
data object Introduction : Screen

@Serializable
data object Login : Screen

@Serializable
data object Register : Screen

@Serializable
data object HomeScreen : Screen

@Serializable
data object BookShelfScreen : Screen

@Serializable
data object SearchScreen : Screen

@Serializable
data object ProfileScreen : Screen

@Serializable
data class SavedBooksScreen (val books: List<BookUi>): Screen

@Serializable
data class ReviewScreen(val bookUi: BookUi) : Screen

@Serializable
data class BookDetails(val bookId: String) : Screen

@Serializable
data class Read(val url: String) : Screen

