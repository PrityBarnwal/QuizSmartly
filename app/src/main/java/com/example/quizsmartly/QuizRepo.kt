package com.example.quizsmartly

import android.util.Log
import com.example.quizsmartly.roomdb.Question
import com.example.quizsmartly.roomdb.QuestionDao


class QuestionRepository(private val questionDao: QuestionDao) {
    suspend fun getQuestionsByLevel(level: String): List<Question> {
        Log.d("QuestionRepository", "Querying for level: $level")
        val questions = questionDao.getQuestionsByLevel(level)
        Log.d("QuestionRepository", "Questions found: $questions")
        return questions
    }

    suspend fun insertAll(vararg questions: Question) {
        questionDao.insertAll(*questions)
    }

    suspend fun deleteQuestionsByLevel(level: String) {
        Log.d("QuestionRepository", "Deleting questions for level: $level")
        questionDao.deleteQuestionsByLevel(level)
        Log.d("QuestionRepository", "Questions deleted for level: $level")
    }

    suspend fun deleteAllQuestions() {
        Log.d("QuestionRepository", "Deleting all questions")
        questionDao.deleteAllQuestions()
        Log.d("QuestionRepository", "All questions deleted")
    }
}


