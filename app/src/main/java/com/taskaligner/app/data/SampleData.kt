package com.taskaligner.app.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import com.taskaligner.app.data.model.Badge
import com.taskaligner.app.data.model.Job

object SampleData {
    val freelancerBadges = listOf(
        Badge("1", Icons.Default.Star, "Top Freelancer"),
        Badge("2", Icons.Default.Bolt, "Fast Delivery"),
        Badge("3", Icons.Default.LocalFireDepartment, "Trending Talent")
    )

    val clientBadges = listOf(
        Badge("4", Icons.Default.VerifiedUser, "Verified Client"),
        Badge("5", Icons.Default.Star, "Trusted Recruiter"),
        Badge("6", Icons.Default.BusinessCenter, "High Budget Poster")
    )

    val availableJobs = listOf(
        Job("j1", "Build Android E-commerce App", "Looking for an experienced Android developer to build a modern e-commerce app using Jetpack Compose.", "$2,500 - $4,000", "Mobile Development", "TechCorp Inc.", "corp1", "2 hours ago"),
        Job("j2", "UI/UX Design for SaaS Platform", "Need a clean, minimal design for our upcoming B2B SaaS dashboard.", "$1,200", "Design", "SaaS Start", "corp2", "5 hours ago", listOf(clientBadges[0])),
        Job("j3", "Kotlin Backend Developer", "We need someone to write REST APIs using Ktor for our mobile application.", "$3,000/mo", "Backend", "AppWorks", "corp3", "1 day ago")
    )

    val postedJobs = listOf(
        Job("p1", "Senior Android Dev Needed", "We are scaling our team and need a senior dev to lead the mobile squad.", "$80/hr", "Mobile Development", "Startup Inc", "u2", "1 week ago", clientBadges),
        Job("p2", "Logo Design", "Simple modern logo for my new coffee shop.", "$200", "Design", "Startup Inc", "u2", "2 weeks ago", clientBadges)
    )
}
