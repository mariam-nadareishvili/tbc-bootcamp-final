package com.example.android_bootcamp.presentation.navigation

import BookShelfScreen
import HomeScreen
import ProfileScreen
import Screen
import SearchScreen

sealed class BottomNavItem(val screen: Screen, var label: String) {
    data object Home : BottomNavItem(HomeScreen, "Home")
    data object Search : BottomNavItem(SearchScreen, "Search")
    data object BookShelf : BottomNavItem(BookShelfScreen, "Bookshelf")
    data object Profile : BottomNavItem(ProfileScreen, "Profile")
}