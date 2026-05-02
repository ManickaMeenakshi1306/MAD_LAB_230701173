package com.taskaligner.app.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taskaligner.app.data.AppState
import com.taskaligner.app.data.SampleData
import com.taskaligner.app.data.model.UserRole
import com.taskaligner.app.ui.components.BadgeChip
import com.taskaligner.app.ui.theme.PrimaryGradient
import com.taskaligner.app.ui.theme.WarningColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    onLogout: () -> Unit = {}
) {
    val role = AppState.currentRole
    val badges = if (role == UserRole.FREELANCER) SampleData.freelancerBadges else SampleData.clientBadges

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        // Avatar Placeholder
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(PrimaryGradient),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "U",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.surface,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = AppState.currentUser?.name ?: "User Name",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = AppState.currentUser?.headline ?: "TaskAligner User",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(label = if (role == UserRole.FREELANCER) "Jobs Done" else "Jobs Posted", value = "12")
            StatItem(label = if (role == UserRole.FREELANCER) "Earned" else "Spent", value = "$4.5k")
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "4.9",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = WarningColor,
                        modifier = Modifier.size(18.dp).padding(start = 2.dp)
                    )
                }
                Text(
                    text = "Rating",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        Divider(color = MaterialTheme.colorScheme.outlineVariant)
        Spacer(modifier = Modifier.height(24.dp))
        
        // Badges Section
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Achievements",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                badges.forEach { badge ->
                    BadgeChip(badge = badge)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        Divider(color = MaterialTheme.colorScheme.outlineVariant)
        Spacer(modifier = Modifier.height(24.dp))
        
        // My Active Proposals Section (Freelancer Only)
        if (role == UserRole.FREELANCER) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "My Active Proposals",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                val myBids = AppState.bids.filter { it.freelancerId == AppState.currentUser?.id }
                if (myBids.isEmpty()) {
                    Text(
                        text = "You haven't placed any bids yet.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    myBids.forEach { bid ->
                        val jobTitle = AppState.jobs.find { it.id == bid.jobId }?.title ?: "Unknown Job"
                        val statusColor = if (bid.status == com.taskaligner.app.data.model.BidStatus.APPROVED) {
                            com.taskaligner.app.ui.theme.PrimaryGreen
                        } else {
                            com.taskaligner.app.ui.theme.WarningColor
                        }
                        
                        androidx.compose.material3.Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            colors = androidx.compose.material3.CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = jobTitle,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1,
                                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = bid.amount,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                
                                Text(
                                    text = bid.status.name,
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = statusColor,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Divider(color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Role specific info
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = if (role == UserRole.FREELANCER) "Skills" else "About the Company",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = if (role == UserRole.FREELANCER) 
                    "Kotlin, Jetpack Compose, UI/UX Design, Figma, REST APIs, Git, Firebase."
                else 
                    "We are a fast-growing tech startup looking for talented freelancers to help us build the next generation of mobile applications.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.5f
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        Divider(color = MaterialTheme.colorScheme.outlineVariant)
        Spacer(modifier = Modifier.height(24.dp))
        
        // App Settings & Logout
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dark Mode",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                androidx.compose.material3.Switch(
                    checked = AppState.isDarkMode,
                    onCheckedChange = { AppState.toggleTheme() },
                    colors = androidx.compose.material3.SwitchDefaults.colors(
                        checkedThumbColor = com.taskaligner.app.ui.theme.PrimaryBlue,
                        checkedTrackColor = com.taskaligner.app.ui.theme.PrimaryBlue.copy(alpha = 0.5f)
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            androidx.compose.material3.Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Logout")
            }
        }
        
        Spacer(modifier = Modifier.height(80.dp)) // Padding for bottom nav
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
