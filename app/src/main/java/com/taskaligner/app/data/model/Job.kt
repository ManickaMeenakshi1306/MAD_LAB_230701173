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
    val badges: List<Badge> = emptyList(),
    val status: JobStatus = JobStatus.PENDING,
    val assignedFreelancerId: String? = null
)

enum class JobStatus {
    PENDING, PROPOSAL_SUBMITTED, FREELANCER_ASSIGNED, TASK_DELEGATION, COMPLETED, REVIEW
}
