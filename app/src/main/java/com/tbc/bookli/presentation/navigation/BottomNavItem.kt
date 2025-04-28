package com.tbc.bookli.presentation.navigation

import androidx.annotation.StringRes
import com.tbc.bookli.R

sealed class BottomNavItem(val screen: Screen, @StringRes var labelId: Int) {
    data object Home : BottomNavItem(HomeScreen, R.string.home)
    data object Search : BottomNavItem(SearchScreen, R.string.search)
    data object BookShelf : BottomNavItem(BookShelfScreen, R.string.bookshelf)
    data object Profile : BottomNavItem(ProfileScreen, R.string.profile)
}