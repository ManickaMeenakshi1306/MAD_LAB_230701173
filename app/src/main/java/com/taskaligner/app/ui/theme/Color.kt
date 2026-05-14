package com.taskaligner.app.ui.theme

import androidx.compose.ui.graphics.Color

val PrimaryBlue = Color(0xFF3B82F6)
val PrimaryTeal = Color(0xFF14B8A6)
val PrimaryGreen = Color(0xFF22C55E)

val BackgroundLight = Color(0xFFF8FAFC)
val SurfaceLight = Color(0xFFFFFFFF)
val TextPrimaryLight = Color(0xFF0F172A)
val TextSecondaryLight = Color(0xFF64748B)

val BackgroundDark = Color(0xFF0F172A)
val SurfaceDark = Color(0xFF1E293B)
val TextPrimaryDark = Color(0xFFF8FAFC)
val TextSecondaryDark = Color(0xFF94A3B8)

val DividerColor = Color(0xFFE2E8F0)
val WarningColor = Color(0xFFF59E0B)
val ErrorColor = Color(0xFFEF4444)

val PrimaryGradient = androidx.compose.ui.graphics.Brush.linearGradient(
    colors = listOf(PrimaryBlue, PrimaryTeal)
)
val SuccessGradient = androidx.compose.ui.graphics.Brush.linearGradient(
    colors = listOf(PrimaryTeal, PrimaryGreen)
)
val WarningGradient = androidx.compose.ui.graphics.Brush.linearGradient(
    colors = listOf(Color(0xFFF59E0B), Color(0xFFD97706))
)
val DangerGradient = androidx.compose.ui.graphics.Brush.linearGradient(
    colors = listOf(Color(0xFFEF4444), Color(0xFFB91C1C))
)
