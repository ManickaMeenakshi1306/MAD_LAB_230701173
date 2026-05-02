package com.taskaligner.app.ui.screens.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taskaligner.app.data.AppState
import com.taskaligner.app.data.SampleData
import com.taskaligner.app.data.model.UserRole
import com.taskaligner.app.ui.components.GradientButton
import com.taskaligner.app.ui.components.GradientCard
import com.taskaligner.app.ui.components.JobCard
import com.taskaligner.app.ui.components.QuickActionButton
import com.taskaligner.app.ui.components.RoleToggle
import com.taskaligner.app.ui.components.BidDialog

@Composable
fun HomeScreen(
    onNavigateToJobs: () -> Unit,
    onNavigateToPost: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToJobBids: (String) -> Unit = {}
) {
    val currentRole = AppState.currentRole
    var selectedJobIdForBid by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
            HeaderSection(currentRole)
        }

        item {
            RoleToggle(
                currentRole = currentRole,
                onRoleChanged = { AppState.toggleRole() },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Crossfade(
                targetState = currentRole,
                animationSpec = tween(500),
                label = "BannerCrossfade"
            ) { role ->
                BannerCard(
                    role = role,
                    onActionClick = if (role == UserRole.FREELANCER) onNavigateToJobs else onNavigateToPost
                )
            }
        }

        item {
            Crossfade(
                targetState = currentRole,
                animationSpec = tween(400),
                label = "QuickActionsCrossfade"
            ) { role ->
                QuickActionsRow(
                    role = role,
                    onNavigateToJobs = onNavigateToJobs,
                    onNavigateToPost = onNavigateToPost,
                    onNavigateToProfile = onNavigateToProfile
                )
            }
        }

        item {
            Text(
                text = if (currentRole == UserRole.FREELANCER) "Recommended Jobs" else "Recent Postings",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        val displayJobs = if (currentRole == UserRole.FREELANCER) {
            AppState.jobs.filter { it.posterId != AppState.currentUser?.id }.take(2)
        } else {
            AppState.jobs.filter { it.posterId == AppState.currentUser?.id }
        }

        items(displayJobs) { job ->
            val myBid = AppState.bids.find { it.jobId == job.id && it.freelancerId == AppState.currentUser?.id }
            JobCard(
                job = job,
                myBid = myBid,
                onPlaceBidClick = { selectedJobIdForBid = job.id },
                onViewBidsClick = { onNavigateToJobBids(job.id) }
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(80.dp)) // padding for bottom nav
        }
    }
    
    selectedJobIdForBid?.let { jobId ->
        BidDialog(
            jobId = jobId,
            onDismiss = { selectedJobIdForBid = null },
            onSubmit = { amount, proposal ->
                AppState.addBid(jobId, amount, proposal)
                selectedJobIdForBid = null
            }
        )
    }
}

@Composable
private fun HeaderSection(role: UserRole) {
    Column {
        Text(
            text = "Hi, ${AppState.currentUser?.name ?: "User"} 👋",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = if (role == UserRole.FREELANCER) "Find your next opportunity" else "Manage your projects easily",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun BannerCard(role: UserRole, onActionClick: () -> Unit) {
    GradientCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (role == UserRole.FREELANCER) "Ready to take on new projects?" else "Looking for top talent?",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            GradientButton(
                text = if (role == UserRole.FREELANCER) "Explore Opportunities" else "Hire Top Talent",
                onClick = onActionClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun QuickActionsRow(
    role: UserRole,
    onNavigateToJobs: () -> Unit,
    onNavigateToPost: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (role == UserRole.FREELANCER) {
            QuickActionButton(icon = Icons.Default.Search, label = "Find Work", onClick = onNavigateToJobs)
            QuickActionButton(icon = Icons.Default.Person, label = "Profile", onClick = onNavigateToProfile)
        } else {
            QuickActionButton(icon = Icons.Default.Add, label = "Create", onClick = onNavigateToPost)
            QuickActionButton(icon = Icons.Default.Search, label = "Talent", onClick = onNavigateToJobs)
            QuickActionButton(icon = Icons.Default.Person, label = "Profile", onClick = onNavigateToProfile)
        }
    }
}
