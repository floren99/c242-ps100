package com.mcaps.mmm.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user_data")
data class UserData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val score: List<Int>,
    val quiz1: Int,
    val quiz2: Int,
    val quiz3: Int,
    val quiz4: Int,
    val interest: List<Int>,
    val predictedValue: String,
    val date: Date
)
