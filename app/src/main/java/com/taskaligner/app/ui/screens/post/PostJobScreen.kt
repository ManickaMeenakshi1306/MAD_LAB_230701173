package com.taskaligner.app.ui.screens.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.taskaligner.app.ui.components.GradientButton

@Composable
fun PostJobScreen(onJobPosted: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Post a New Job",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Job Title") },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = com.taskaligner.app.ui.theme.PrimaryBlue
            )
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Job Description") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 4,
            maxLines = 8,
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = com.taskaligner.app.ui.theme.PrimaryBlue
            )
        )

        OutlinedTextField(
            value = budget,
            onValueChange = { budget = it },
            label = { Text("Budget (e.g. $500 - $1000)") },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = com.taskaligner.app.ui.theme.PrimaryBlue
            )
        )

        GradientButton(
            text = "Post Job",
            onClick = {
                // In a real app, save the job
                onJobPosted()
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
