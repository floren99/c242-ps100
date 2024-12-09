package com.mcaps.mmm.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mcaps.mmm.data.local.entity.UserData

@Dao
interface UserDataDao {

    @Insert
    suspend fun insertUserData(userData: UserData)

    @Query("SELECT * FROM user_data")
    fun getAllData(): LiveData<List<UserData>>

    @Query("SELECT * FROM user_data WHERE id = :id LIMIT 1")
    suspend fun getUserData(id: Long): UserData?

    @Query("DELETE FROM user_data WHERE id = :id")
    suspend fun deleteUserData(id: Long)
}