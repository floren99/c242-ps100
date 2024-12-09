package com.mcaps.mmm.data.local.dao

import androidx.room.TypeConverter
import java.util.Date

class Converters {

    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun fromList(value: List<String>): String {
        return value.joinToString(",")
    }

    // Converts Date to Long and back
    @TypeConverter
    fun fromDate(value: Date): Long {
        return value.time
    }

    @TypeConverter
    fun toDate(value: Long): Date {
        return Date(value)
    }
}