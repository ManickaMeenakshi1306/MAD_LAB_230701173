package com.taskaligner.app.ui.screens.auth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taskaligner.app.ui.theme.PrimaryBlue
import com.taskaligner.app.ui.theme.PrimaryGreen
import kotlinx.coroutines.launch

data class OnboardingPage(
    val title: String,
    val description: String,
    val icon: ImageVector
)

val onboardingPages = listOf(
    OnboardingPage(
        "Find Top Talent",
        "Connect with verified professionals who are ready to bring your vision to life.",
        Icons.Default.Group
    ),
    OnboardingPage(
        "Gamified Trust",
        "Earn and view badges that signify reliability, quality, and fast delivery.",
        Icons.Default.Star
    ),
    OnboardingPage(
        "Real-time Chat",
        "Communicate seamlessly with your clients and freelancers right in the app.",
        Icons.Default.Chat
    ),
    OnboardingPage(
        "Secure Platform",
        "Your data and transactions are protected with industry-leading security.",
        Icons.Default.Security
    )
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            OnboardingPageContent(page = onboardingPages[page])
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(onboardingPages.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) PrimaryBlue else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 48.dp)
        ) {
            if (pagerState.currentPage == onboardingPages.lastIndex) {
                Button(
                    onClick = onFinish,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text("Get Started", style = MaterialTheme.typography.titleMedium)
                }
            } else {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) {
                    Text("Next", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

@Composable
fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = page.icon,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = PrimaryBlue
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}
