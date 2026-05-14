package com.taskaligner.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.taskaligner.app.ui.components.BottomNavBar
import com.taskaligner.app.ui.navigation.Screen
import com.taskaligner.app.ui.navigation.TaskAlignerNavGraph
import com.taskaligner.app.ui.theme.TaskAlignerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        com.taskaligner.app.data.AppState.initialize(this)
        setContent {
            TaskAlignerTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                
                val showBottomNav = currentRoute in listOf(
                    Screen.Home.route,
                    Screen.Jobs.route,
                    Screen.Post.route,
                    Screen.Profile.route
                )
                
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { 
                        if (showBottomNav) {
                            BottomNavBar(navController = navController) 
                        }
                    }
                ) { innerPadding ->
                    TaskAlignerNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
