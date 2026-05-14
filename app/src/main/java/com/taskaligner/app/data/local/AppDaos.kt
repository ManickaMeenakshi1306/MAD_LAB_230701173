package com.taskaligner.app.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
}

@Dao
interface JobDao {
    @Query("SELECT * FROM jobs")
    fun getAllJobs(): Flow<List<JobEntity>>

    @Query("SELECT * FROM jobs WHERE clientId = :clientId")
    fun getJobsByClient(clientId: String): Flow<List<JobEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobEntity)

    @Update
    suspend fun updateJob(job: JobEntity)
}

@Dao
interface BidDao {
    @Query("SELECT * FROM bids WHERE jobId = :jobId")
    fun getBidsForJob(jobId: String): Flow<List<BidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBid(bid: BidEntity)

    @Update
    suspend fun updateBid(bid: BidEntity)
}

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE jobId = :jobId")
    fun getTasksForJob(jobId: String): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)
}

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE jobId = :jobId ORDER BY timestamp ASC")
    fun getMessagesForJob(jobId: String): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)
}

@Dao
interface EarningDao {
    @Query("SELECT * FROM earnings WHERE userId = :userId")
    fun getEarningsForUser(userId: String): Flow<List<EarningEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEarning(earning: EarningEntity)
}

@Dao
interface StressLogDao {
    @Query("SELECT * FROM stress_logs WHERE userId = :userId ORDER BY timestamp DESC")
    fun getStressLogsForUser(userId: String): Flow<List<StressLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStressLog(log: StressLogEntity)
}

@Dao
interface GameActivityDao {
    @Query("SELECT * FROM game_activity WHERE userId = :userId ORDER BY timestamp DESC")
    fun getGameActivityForUser(userId: String): Flow<List<GameActivityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameActivity(activity: GameActivityEntity)
}
