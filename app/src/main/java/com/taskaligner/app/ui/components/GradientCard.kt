package com.taskaligner.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.taskaligner.app.ui.theme.PrimaryGradient

@Composable
fun GradientCard(
    modifier: Modifier = Modifier,
    gradient: Brush = PrimaryGradient,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = com.taskaligner.app.ui.theme.PrimaryBlue.copy(alpha = 0.25f)
            )
            .clip(RoundedCornerShape(24.dp))
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .background(gradient),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        content()
    }
}
