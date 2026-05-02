package com.taskaligner.app.data.model

data class User(
    val id: String,
    val name: String,
    val role: UserRole,
    val headline: String,
    val badges: List<Badge> = emptyList()
)
