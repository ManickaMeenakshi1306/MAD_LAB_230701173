package com.taskaligner.app.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.taskaligner.app.data.AppState
import com.taskaligner.app.data.SampleData
import com.taskaligner.app.ui.components.BadgeChip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    bidId: String,
    onNavigateBack: () -> Unit
) {
    val bid = AppState.bids.find { it.id == bidId }
    val chatPartnerName = if (AppState.currentRole == com.taskaligner.app.data.model.UserRole.CLIENT) {
        bid?.freelancerName ?: "Freelancer"
    } else {
        "Startup Inc" // Simplification for prototype
    }
    
    val badges = if (AppState.currentRole == com.taskaligner.app.data.model.UserRole.CLIENT) {
        SampleData.freelancerBadges.take(2)
    } else {
        SampleData.clientBadges.take(1)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text(chatPartnerName, style = MaterialTheme.typography.titleMedium)
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            badges.forEach { badge ->
                                BadgeChip(badge = badge)
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Type a message...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp)
                )
                IconButton(onClick = { /* Send message */ }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = com.taskaligner.app.ui.theme.PrimaryBlue
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            ChatBubble(text = "Hi, thanks for accepting my bid! When can we start?", isFromMe = false)
        }
    }
}

@Composable
fun ChatBubble(text: String, isFromMe: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isFromMe) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (isFromMe) com.taskaligner.app.ui.theme.PrimaryBlue else MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomEnd = if (isFromMe) 0.dp else 16.dp,
                        bottomStart = if (isFromMe) 16.dp else 0.dp
                    )
                )
                .padding(12.dp)
        ) {
            Text(
                text = text,
                color = if (isFromMe) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
