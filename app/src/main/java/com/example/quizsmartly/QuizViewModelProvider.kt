package com.example.quizsmartly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider



class QuizViewModelFactory(private val repository: QuestionRepository) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)){
            return QuizViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel not found")
    }
}


