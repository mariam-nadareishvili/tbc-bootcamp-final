package com.tbc.bookli.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination

@Composable
fun BottomBar(
    items: List<BottomNavItem>,
    currentDestination: NavDestination?,
    onItemClick: (BottomNavItem) -> Unit
) {
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.route == item.screen::class.qualifiedName,
                onClick = { onItemClick(item) },
                label = { Text(text = stringResource(id = item.labelId)) },
                icon = {
                    when (item) {
                        is BottomNavItem.Home -> Icon(Icons.Default.Home, contentDescription = null)
                        is BottomNavItem.Search -> Icon(Icons.Default.Search, contentDescription = null)
                        is BottomNavItem.BookShelf -> Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null)
                        is BottomNavItem.Profile -> Icon(Icons.Default.Person, contentDescription = null)
                    }
                }
            )
        }
    }
}
