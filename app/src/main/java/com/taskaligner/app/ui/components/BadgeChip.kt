package com.taskaligner.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taskaligner.app.data.model.Badge
import com.taskaligner.app.ui.theme.PrimaryGradient

@Composable
fun BadgeChip(badge: Badge, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .background(brush = PrimaryGradient, alpha = 0.15f)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = badge.icon,
            contentDescription = badge.label,
            tint = com.taskaligner.app.ui.theme.PrimaryTeal,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = badge.label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
        )
    }
}
