package com.taskaligner.app.ui.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taskaligner.app.ui.components.GradientButton

import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDelegationScreen(jobId: String, onNavigateBack: () -> Unit) {
    var showAddTaskDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Delegation") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showAddTaskDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Task")
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
            Text(
                text = "Project: Build Android E-commerce App",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 24.dp)
            )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(sampleTasks) { task ->
                TaskItem(task)
            }
        }
    }
    
    if (showAddTaskDialog) {
        AddTaskDialog(onDismiss = { showAddTaskDialog = false })
    }
}
}

@Composable
fun TaskItem(task: SubTask) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                if (task.status == "Done") Icons.Default.CheckCircle else Icons.Default.PendingActions,
                contentDescription = null,
                tint = if (task.status == "Done") Color(0xFF22C55E) else Color(0xFF3B82F6),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(task.title, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AssignmentInd, null, modifier = Modifier.size(12.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(task.assignedTo, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }
            Text(
                task.status,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .background(
                        if (task.status == "Done") Color(0xFF22C55E).copy(alpha = 0.1f) else Color(0xFF3B82F6).copy(alpha = 0.1f),
                        RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                color = if (task.status == "Done") Color(0xFF22C55E) else Color(0xFF3B82F6)
            )
        }
    }
}

@Composable
fun AddTaskDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delegate New Task") },
        text = {
            Column {
                OutlinedTextField(value = "", onValueChange = {}, label = { Text("Task Title") })
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = "", onValueChange = {}, label = { Text("Assign to (Freelancer ID)") })
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) { Text("Assign") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

data class SubTask(val title: String, val assignedTo: String, val status: String)

val sampleTasks = listOf(
    SubTask("Design Checkout Flow", "User_442", "Done"),
    SubTask("API Integration", "User_109", "In Progress"),
    SubTask("Testing & QA", "User_212", "Todo")
)
