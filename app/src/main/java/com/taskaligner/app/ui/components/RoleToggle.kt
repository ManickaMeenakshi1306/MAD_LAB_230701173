package com.taskaligner.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taskaligner.app.data.model.UserRole
import com.taskaligner.app.ui.theme.PrimaryGradient

@Composable
fun RoleToggle(
    currentRole: UserRole,
    onRoleChanged: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(4.dp)
            .clickable { onRoleChanged() }
    ) {
        RoleTab(
            text = "Freelancer",
            isSelected = currentRole == UserRole.FREELANCER,
            modifier = Modifier.weight(1f)
        )
        RoleTab(
            text = "Client",
            isSelected = currentRole == UserRole.CLIENT,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun RoleTab(
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(300),
        label = "RoleTextColor"
    )

    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(20.dp))
            .then(if (isSelected) Modifier.background(PrimaryGradient) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}
