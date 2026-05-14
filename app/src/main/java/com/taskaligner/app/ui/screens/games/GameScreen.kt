package com.taskaligner.app.ui.screens.games

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(onNavigateBack: () -> Unit) {
    var gameState by remember { mutableStateOf(GameState.IDLE) }
    var startTime by remember { mutableLongStateOf(0L) }
    var reactionTime by remember { mutableLongStateOf(0L) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Focus Boost") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Focus Boost",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        Text(
            text = "Tap when the circle turns GREEN!",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        Box(
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
                .background(
                    when (gameState) {
                        GameState.IDLE -> Color.Gray.copy(alpha = 0.2f)
                        GameState.WAITING -> Color(0xFFEF4444)
                        GameState.READY -> Color(0xFF22C55E)
                        GameState.RESULT -> Color(0xFF3B82F6)
                    }
                )
                .clickable {
                    when (gameState) {
                        GameState.IDLE -> {
                            gameState = GameState.WAITING
                        }
                        GameState.WAITING -> {
                            // Too early!
                            gameState = GameState.IDLE
                        }
                        GameState.READY -> {
                            reactionTime = System.currentTimeMillis() - startTime
                            gameState = GameState.RESULT
                        }
                        GameState.RESULT -> {
                            gameState = GameState.WAITING
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            when (gameState) {
                GameState.IDLE -> Icon(Icons.Default.PlayArrow, null, modifier = Modifier.size(80.dp), tint = Color.Gray)
                GameState.WAITING -> Text("Wait for it...", color = Color.White, fontWeight = FontWeight.Bold)
                GameState.READY -> Text("TAP NOW!", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                GameState.RESULT -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${reactionTime}ms", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 32.sp)
                    Text("Tap to restart", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                }
            }
        }

        LaunchedEffect(gameState) {
            if (gameState == GameState.WAITING) {
                delay(Random.nextLong(1000, 4000))
                if (gameState == GameState.WAITING) {
                    startTime = System.currentTimeMillis()
                    gameState = GameState.READY
                }
            }
        }
        
        if (gameState == GameState.RESULT && reactionTime > 0) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = if (reactionTime < 300) "Lightning Fast! ⚡" else "Good Job! Keep focusing.",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
}

enum class GameState {
    IDLE, WAITING, READY, RESULT
}
