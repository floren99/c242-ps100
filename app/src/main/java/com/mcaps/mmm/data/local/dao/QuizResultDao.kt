package com.mcaps.mmm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mcaps.mmm.data.local.entity.QuizResultEntity

@Dao
interface QuizResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizResult(result: QuizResultEntity)

    @Query("SELECT * FROM quiz_results")
    suspend fun getAllQuizResults(): List<QuizResultEntity>

    @Query("SELECT * FROM quiz_results WHERE quizId = :quizId LIMIT 1")
    suspend fun getQuizResultById(quizId: Int): QuizResultEntity?
}
