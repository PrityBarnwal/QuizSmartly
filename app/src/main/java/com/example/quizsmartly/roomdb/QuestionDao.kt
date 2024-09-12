package com.example.quizsmartly.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg questions: Question)

    @Query("SELECT * FROM Question WHERE LOWER(level) = LOWER(:level)")
    suspend fun getQuestionsByLevel(level: String): List<Question>

    @Query("DELETE FROM Question WHERE level = :level")
    suspend fun deleteQuestionsByLevel(level: String)

    @Query("DELETE FROM Question")
    suspend fun deleteAllQuestions()
}
