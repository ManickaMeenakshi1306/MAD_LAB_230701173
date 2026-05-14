package com.taskaligner.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.taskaligner.app.ui.screens.home.HomeScreen
import com.taskaligner.app.ui.screens.jobs.JobListScreen
import com.taskaligner.app.ui.screens.post.PostJobScreen
import com.taskaligner.app.ui.screens.profile.ProfileScreen
import com.taskaligner.app.ui.screens.bids.JobBidsScreen
import com.taskaligner.app.ui.screens.chat.ChatScreen
import com.taskaligner.app.ui.screens.auth.LoginScreen
import com.taskaligner.app.ui.screens.auth.OnboardingScreen
import com.taskaligner.app.ui.screens.finance.EarningsScreen
import com.taskaligner.app.ui.screens.wellbeing.StressTrackerScreen
import com.taskaligner.app.ui.screens.games.GameScreen
import com.taskaligner.app.ui.screens.tasks.TaskDelegationScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Composable
fun TaskAlignerNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route,
        modifier = modifier
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { 
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    } 
                }
            )
        }
        
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToJobs = { navController.navigate(Screen.Jobs.route) },
                onNavigateToPost = { navController.navigate(Screen.Post.route) },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onNavigateToJobBids = { jobId -> navController.navigate(Screen.JobBids.createRoute(jobId)) },
                onNavigateToEarnings = { navController.navigate(Screen.Earnings.route) },
                onNavigateToStressTracker = { navController.navigate(Screen.StressTracker.route) },
                onNavigateToGame = { navController.navigate(Screen.Game.route) }
            )
        }
        
        composable(Screen.Jobs.route) {
            JobListScreen(
                onNavigateToJobBids = { jobId -> navController.navigate(Screen.JobBids.createRoute(jobId)) }
            )
        }
        
        composable(Screen.Post.route) {
            PostJobScreen(
                onJobPosted = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.JobBids.route,
            arguments = listOf(navArgument("jobId") { type = NavType.StringType })
        ) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId") ?: ""
            JobBidsScreen(
                jobId = jobId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToChat = { bidId -> navController.navigate(Screen.Chat.createRoute(bidId)) }
            )
        }

        composable(
            route = Screen.Chat.route,
            arguments = listOf(navArgument("bidId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bidId = backStackEntry.arguments?.getString("bidId") ?: ""
            ChatScreen(
                bidId = bidId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Earnings.route) {
            EarningsScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(Screen.StressTracker.route) {
            StressTrackerScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(Screen.Game.route) {
            GameScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(
            route = Screen.TaskDelegation.route,
            arguments = listOf(navArgument("jobId") { type = NavType.StringType })
        ) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId") ?: ""
            TaskDelegationScreen(
                jobId = jobId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

