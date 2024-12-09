package com.mcaps.mmm.data.repository

import com.mcaps.mmm.data.local.dao.UserDataDao
import com.mcaps.mmm.data.local.entity.UserData

class UserDataRepository(private val userDataDao: UserDataDao) {

    suspend fun insertUserData(userData: UserData) {
        userDataDao.insertUserData(userData)
    }

    suspend fun getAllUserData() {
        userDataDao.getAllData()
    }
}