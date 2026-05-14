package com.taskaligner.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val role: String, // CLIENT or FREELANCER
    val headline: String,
    val profilePicUrl: String? = null
)

@Entity(tableName = "jobs")
data class JobEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val budget: String,
    val category: String,
    val clientName: String,
    val clientId: String,
    val postedTime: String,
    val status: String = "PENDING" // PENDING, ONGOING, COMPLETED
)

@Entity(tableName = "bids")
data class BidEntity(
    @PrimaryKey val id: String,
    val jobId: String,
    val freelancerId: String,
    val freelancerName: String,
    val amount: String,
    val proposal: String,
    val status: String = "PENDING" // PENDING, APPROVED, REJECTED
)

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val jobId: String,
    val assignedFreelancerId: String,
    val title: String,
    val description: String,
    val status: String = "TODO" // TODO, IN_PROGRESS, DONE
)

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    val jobId: String,
    val senderId: String,
    val receiverId: String,
    val content: String,
    val timestamp: Long
)

@Entity(tableName = "earnings")
data class EarningEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val jobId: String,
    val amount: Double,
    val status: String = "PAID",
    val timestamp: Long
)

@Entity(tableName = "stress_logs")
data class StressLogEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val level: String, // LOW, MEDIUM, HIGH
    val note: String,
    val timestamp: Long
)

@Entity(tableName = "game_activity")
data class GameActivityEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val gameType: String,
    val score: Int,
    val timestamp: Long
)
