package com.taskaligner.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserEntity::class,
        JobEntity::class,
        BidEntity::class,
        TaskEntity::class,
        MessageEntity::class,
        EarningEntity::class,
        StressLogEntity::class,
        GameActivityEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun jobDao(): JobDao
    abstract fun bidDao(): BidDao
    abstract fun taskDao(): TaskDao
    abstract fun messageDao(): MessageDao
    abstract fun earningDao(): EarningDao
    abstract fun stressLogDao(): StressLogDao
    abstract fun gameActivityDao(): GameActivityDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "freelancer_hub_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
