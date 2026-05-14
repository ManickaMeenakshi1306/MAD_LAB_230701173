package com.taskaligner.app.ui.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Home : Screen("home")
    object Jobs : Screen("jobs")
    object Post : Screen("post")
    object Profile : Screen("profile")
    object JobBids : Screen("jobBids/{jobId}") {
        fun createRoute(jobId: String) = "jobBids/$jobId"
    }
    object Chat : Screen("chat/{bidId}") {
        fun createRoute(bidId: String) = "chat/$bidId"
    }
    object Earnings : Screen("earnings")
    object StressTracker : Screen("stressTracker")
    object Game : Screen("game")
    object TaskDelegation : Screen("taskDelegation/{jobId}") {
        fun createRoute(jobId: String) = "taskDelegation/$jobId"
    }
}
