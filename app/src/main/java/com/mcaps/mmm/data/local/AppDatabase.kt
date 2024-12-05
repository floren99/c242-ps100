package com.mcaps.mmm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mcaps.mmm.data.local.dao.QuizResultDao
import com.mcaps.mmm.data.local.entity.QuizResultEntity

@Database(entities = [QuizResultEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quizResultDao(): QuizResultDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
