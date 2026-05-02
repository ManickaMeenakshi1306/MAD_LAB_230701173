package com.taskaligner.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taskaligner.app.data.AppState
import com.taskaligner.app.data.model.Job
import com.taskaligner.app.data.model.UserRole

import com.taskaligner.app.data.model.Bid

@Composable
fun JobCard(
    job: Job,
    myBid: Bid? = null,
    modifier: Modifier = Modifier,
    onViewBidsClick: () -> Unit = {},
    onPlaceBidClick: () -> Unit = {}
) {
    val currentRole = AppState.currentRole
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = job.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = job.budget,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = com.taskaligner.app.ui.theme.PrimaryGreen
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = job.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            if (job.badges.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    job.badges.forEach { badge ->
                        BadgeChip(badge = badge)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = job.category,
                    style = MaterialTheme.typography.labelMedium,
                    color = com.taskaligner.app.ui.theme.PrimaryTeal
                )
                Text(
                    text = job.timeAgo,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (currentRole == UserRole.FREELANCER) {
                if (myBid != null) {
                    val statusColor = if (myBid.status == com.taskaligner.app.data.model.BidStatus.APPROVED) {
                        com.taskaligner.app.ui.theme.PrimaryGreen
                    } else {
                        com.taskaligner.app.ui.theme.WarningColor
                    }
                    Button(
                        onClick = { },
                        enabled = false,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            disabledContentColor = statusColor
                        )
                    ) {
                        Text("Bid Placed • ${myBid.status.name}")
                    }
                } else {
                    Button(
                        onClick = onPlaceBidClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = com.taskaligner.app.ui.theme.PrimaryBlue
                        )
                    ) {
                        Text("Place Bid")
                    }
                }
            } else {
                TextButton(
                    onClick = onViewBidsClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Review Proposals", color = com.taskaligner.app.ui.theme.PrimaryBlue)
                }
            }
        }
    }
}
