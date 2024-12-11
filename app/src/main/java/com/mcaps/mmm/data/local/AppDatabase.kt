package com.mcaps.mmm.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mcaps.mmm.data.local.dao.UserDataDao
import com.mcaps.mmm.data.local.entity.UserData

@Database(entities = [UserData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDataDao(): UserDataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_data_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
