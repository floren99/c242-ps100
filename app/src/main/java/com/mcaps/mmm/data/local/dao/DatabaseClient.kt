package com.mcaps.mmm.data.local.dao

import android.content.Context
import androidx.room.Room
import com.mcaps.mmm.data.local.AppDatabase

object DatabaseClient {

    private var appDatabase: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase? {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "user_data_db"
            ).build()
        }
        return appDatabase
    }
}