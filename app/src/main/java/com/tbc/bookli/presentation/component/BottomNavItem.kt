package com.tbc.bookli.presentation.component

import androidx.annotation.StringRes
import com.tbc.bookli.core.common.R
import com.tbc.bookli.presentation.navigation.BookShelfScreen
import com.tbc.bookli.presentation.navigation.HomeScreen
import com.tbc.bookli.presentation.navigation.ProfileScreen
import com.tbc.bookli.presentation.navigation.Screen
import com.tbc.bookli.presentation.navigation.SearchScreen

sealed class BottomNavItem(val screen: Screen, @StringRes var labelId: Int) {
    data object Home : BottomNavItem(HomeScreen, R.string.home)
    data object Search : BottomNavItem(SearchScreen, R.string.search)
    data object BookShelf : BottomNavItem(BookShelfScreen, R.string.bookshelf)
    data object Profile : BottomNavItem(ProfileScreen, R.string.profile)
}