package com.taskaligner.app.ui.screens.bids

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taskaligner.app.data.AppState
import com.taskaligner.app.ui.components.BidCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobBidsScreen(
    jobId: String,
    onNavigateBack: () -> Unit,
    onNavigateToChat: (String) -> Unit
) {
    val bids = AppState.bids.filter { it.jobId == jobId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Job Bids") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (bids.isEmpty()) {
                Text(
                    text = "No bids yet for this job.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Text(
                    text = "${bids.size} Bid(s) Received",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(bids) { bid ->
                        BidCard(
                            bid = bid,
                            onApproveClick = { AppState.approveBid(bid.id) },
                            onChatClick = { onNavigateToChat(bid.id) }
                        )
                    }
                }
            }
        }
    }
}
