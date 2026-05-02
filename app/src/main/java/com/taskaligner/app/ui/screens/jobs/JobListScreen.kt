package com.taskaligner.app.ui.screens.jobs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taskaligner.app.data.AppState
import com.taskaligner.app.data.SampleData
import com.taskaligner.app.data.model.UserRole
import com.taskaligner.app.ui.components.JobCard
import com.taskaligner.app.ui.components.BidDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobListScreen(
    onNavigateToJobBids: (String) -> Unit = {}
) {
    val currentRole = AppState.currentRole
    var searchQuery by remember { mutableStateOf("") }
    var selectedJobIdForBid by remember { mutableStateOf<String?>(null) }
    
    val jobs = if (currentRole == UserRole.FREELANCER) {
        AppState.jobs.filter { it.posterId != AppState.currentUser?.id }
    } else {
        AppState.jobs.filter { it.posterId == AppState.currentUser?.id }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Text(
            text = if (currentRole == UserRole.FREELANCER) "Browse Jobs" else "Your Postings",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search jobs...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = com.taskaligner.app.ui.theme.PrimaryBlue,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(jobs) { job ->
                val myBid = AppState.bids.find { it.jobId == job.id && it.freelancerId == AppState.currentUser?.id }
                JobCard(
                    job = job,
                    myBid = myBid,
                    onPlaceBidClick = { selectedJobIdForBid = job.id },
                    onViewBidsClick = { onNavigateToJobBids(job.id) }
                )
            }
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
