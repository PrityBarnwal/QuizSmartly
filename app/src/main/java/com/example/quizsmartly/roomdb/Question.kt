package com.example.quizsmartly.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Question(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val level: String,
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val answer: String
)


