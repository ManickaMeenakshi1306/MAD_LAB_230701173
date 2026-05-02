package com.taskaligner.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun BottomNavBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Jobs", Icons.Default.Work, "jobs"),
        BottomNavItem("Post", Icons.Default.AddCircle, "post"),
        BottomNavItem("Profile", Icons.Default.Person, "profile")
    )

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
            
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = com.taskaligner.app.ui.theme.PrimaryBlue,
                    selectedTextColor = com.taskaligner.app.ui.theme.PrimaryBlue,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = com.taskaligner.app.ui.theme.PrimaryBlue.copy(alpha = 0.1f)
                )
            )
        }
    }
}
