package com.taskaligner.app.data.model

enum class BidStatus {
    PENDING, APPROVED, REJECTED
}

data class Bid(
    val id: String,
    val jobId: String,
    val freelancerId: String,
    val freelancerName: String,
    val amount: String,
    val proposal: String,
    val status: BidStatus = BidStatus.PENDING
)
