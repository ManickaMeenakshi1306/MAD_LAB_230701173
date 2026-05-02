package com.taskaligner.app.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.taskaligner.app.data.model.Bid
import com.taskaligner.app.data.model.BidStatus
import com.taskaligner.app.data.model.User
import com.taskaligner.app.data.model.UserRole
import java.util.UUID

object AppState {
    // Predefined Users for prototyping
    val freelancerUser = User(
        id = "u1",
        name = "Freelancer Pro",
        role = UserRole.FREELANCER,
        headline = "UI/UX Designer & Android Dev",
        badges = SampleData.freelancerBadges
    )
    
    val clientUser = User(
        id = "u2",
        name = "Startup Inc",
        role = UserRole.CLIENT,
        headline = "Tech Startup Founder",
        badges = SampleData.clientBadges
    )

    var currentUser by mutableStateOf<User?>(null)
    
    // Default to dark mode is false (light mode), let user toggle
    var isDarkMode by mutableStateOf(false)

    val currentRole: UserRole
        get() = currentUser?.role ?: UserRole.FREELANCER
    
    val bids = mutableStateListOf<Bid>()

    fun toggleRole() {
        // Obsolete, left to avoid compilation errors during refactoring
        currentUser = if (currentRole == UserRole.FREELANCER) clientUser else freelancerUser
    }
    
    fun toggleTheme() {
        isDarkMode = !isDarkMode
    }
    
    fun addBid(jobId: String, amount: String, proposal: String) {
        val newBid = Bid(
            id = UUID.randomUUID().toString(),
            jobId = jobId,
            freelancerId = currentUser?.id ?: "unknown",
            freelancerName = currentUser?.name ?: "Unknown",
            amount = amount,
            proposal = proposal,
            status = BidStatus.PENDING
        )
        bids.add(newBid)
    }
    
    fun approveBid(bidId: String) {
        val index = bids.indexOfFirst { it.id == bidId }
        if (index != -1) {
            bids[index] = bids[index].copy(status = BidStatus.APPROVED)
        }
    }
    
    val jobs = mutableStateListOf<com.taskaligner.app.data.model.Job>()
    
    init {
        jobs.addAll(SampleData.availableJobs + SampleData.postedJobs)
        
        bids.addAll(
            listOf(
                Bid("b1", "p1", "mock_f1", "Alice Smith", "$75/hr", "I have 5 years of Android experience.", BidStatus.PENDING),
                Bid("b2", "p1", "mock_f2", "Bob Johnson", "$85/hr", "Senior dev here, I can lead this.", BidStatus.PENDING),
                Bid("b3", "p2", "mock_f3", "Design Pro", "$150", "I can do this in 2 days.", BidStatus.PENDING)
            )
        )
    }
}

