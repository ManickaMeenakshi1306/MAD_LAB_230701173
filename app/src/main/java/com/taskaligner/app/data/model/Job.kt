package com.taskaligner.app.data.model

data class Job(
    val id: String,
    val title: String,
    val description: String,
    val budget: String,
    val category: String,
    val postedBy: String,
    val posterId: String,
    val timeAgo: String,
    val badges: List<Badge> = emptyList()
)
