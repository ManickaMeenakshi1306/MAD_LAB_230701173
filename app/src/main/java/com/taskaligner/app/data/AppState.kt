package com.taskaligner.app.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.taskaligner.app.data.model.Bid
import com.taskaligner.app.data.model.BidStatus
import com.taskaligner.app.data.model.User
import com.taskaligner.app.data.model.UserRole
import android.content.Context
import java.util.UUID
import androidx.lifecycle.ViewModel
import com.taskaligner.app.data.local.AppDatabase
import com.taskaligner.app.data.local.UserEntity
import com.taskaligner.app.data.local.JobEntity
import com.taskaligner.app.data.local.EarningEntity
import com.taskaligner.app.data.local.StressLogEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object AppState {
    var database: AppDatabase? = null
        private set
    private val scope = CoroutineScope(Dispatchers.IO)

    fun initialize(context: Context) {
        if (database == null) {
            database = AppDatabase.getDatabase(context)
            
            // Seed initial data if needed
            scope.launch {
                database?.userDao()?.insertUser(
                    UserEntity(freelancerUser.id, freelancerUser.name, "FREELANCER", freelancerUser.headline)
                )
                database?.userDao()?.insertUser(
                    UserEntity(clientUser.id, clientUser.name, "CLIENT", clientUser.headline)
                )
            }
        }
    }

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

    var currentUser by mutableStateOf<User?>(freelancerUser) // Default to freelancer for demo
    
    // Default to dark mode is false (light mode), let user toggle
    var isDarkMode by mutableStateOf(false)

    val currentRole: UserRole
        get() = currentUser?.role ?: UserRole.FREELANCER
    
    val bids = mutableStateListOf<Bid>()

    fun toggleRole() {
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
            
            // Add to earnings when approved
            val bid = bids[index]
            val amountStr = bid.amount.replace("$", "").replace("/hr", "").toDoubleOrNull() ?: 0.0
            scope.launch {
                database?.earningDao()?.insertEarning(
                    EarningEntity(
                        id = UUID.randomUUID().toString(),
                        userId = bid.freelancerId,
                        jobId = bid.jobId,
                        amount = amountStr,
                        timestamp = System.currentTimeMillis()
                    )
                )
            }
        }
    }

    fun getEarnings() = database?.earningDao()?.getEarningsForUser(currentUser?.id ?: "")

    fun getStressLogs() = database?.stressLogDao()?.getStressLogsForUser(currentUser?.id ?: "")

    fun logStress(level: String, note: String) {
        scope.launch {
            database?.stressLogDao()?.insertStressLog(
                StressLogEntity(
                    id = UUID.randomUUID().toString(),
                    userId = currentUser?.id ?: "guest",
                    level = level,
                    note = note,
                    timestamp = System.currentTimeMillis()
                )
            )
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

