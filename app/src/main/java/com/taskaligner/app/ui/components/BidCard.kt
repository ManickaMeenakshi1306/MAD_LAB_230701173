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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taskaligner.app.data.SampleData
import com.taskaligner.app.data.model.Bid
import com.taskaligner.app.data.model.BidStatus

@Composable
fun BidCard(
    bid: Bid,
    onApproveClick: () -> Unit,
    onChatClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = bid.freelancerName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = bid.amount,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = com.taskaligner.app.ui.theme.PrimaryGreen
                )
            }
            
            // Add a gamification badge for visual appeal
            if (bid.freelancerName.contains("Pro") || bid.freelancerName.contains("Alice")) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    BadgeChip(badge = SampleData.freelancerBadges[0]) // Top Freelancer
                    BadgeChip(badge = SampleData.freelancerBadges[1]) // Fast Delivery
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = bid.proposal,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (bid.status == BidStatus.PENDING) {
                Button(
                    onClick = onApproveClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = com.taskaligner.app.ui.theme.PrimaryTeal
                    )
                ) {
                    Text("Accept Proposal & Connect")
                }
            } else if (bid.status == BidStatus.APPROVED) {
                Button(
                    onClick = onChatClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = com.taskaligner.app.ui.theme.PrimaryBlue
                    )
                ) {
                    Text("Chat Enabled - Open Chat")
                }
            }
        }
    }
}

