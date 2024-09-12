package com.example.quizsmartly

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizsmartly.roomdb.Question
import kotlinx.coroutines.launch


class QuizViewModel(private val repository: QuestionRepository) : ViewModel() {

    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> = _questions

    private val _currentQuestionIndex = MutableLiveData(0)
    val currentQuestionIndex: LiveData<Int> = _currentQuestionIndex

    private val _score = MutableLiveData(0)
    val score: LiveData<Int> = _score

    private val _totalQuestions = MutableLiveData<Int>(10)
    val totalQuestions: LiveData<Int> = _totalQuestions

    private val _showResult = MutableLiveData<Boolean>(false)
    val showResult: LiveData<Boolean> = _showResult


    private var questionLevel: String? = null

    fun setLevel(level: String) {
        questionLevel = level
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            try {
                val level = questionLevel ?: return@launch
                repository.deleteQuestionsByLevel(level)
                Log.d("QuizViewModel", "Loading questions for level: $level")
                val loadedQuestions = repository.getQuestionsByLevel(level)
                Log.d("QuizViewModel", "Questions loaded: $loadedQuestions")
                if (loadedQuestions.isNotEmpty()) {
                    _questions.value = loadedQuestions
                    _totalQuestions.value = loadedQuestions.size
                    _currentQuestionIndex.value = 0
                } else {
                    Log.d("QuizViewModel", "No questions available for this level.")
                }
            } catch (e: Exception) {
                Log.e("QuizViewModel", "Error loading questions: ${e.message}")
            }
        }
    }

    fun checkAnswer(selectedOption: String) {
        val question = questions.value?.get(currentQuestionIndex.value ?: 0)
        if (question?.answer == selectedOption) {
            _score.value = (_score.value ?: 0) + 1
            Log.d("QuizViewModel", "Correct answer! Score: ${_score.value}")
        } else {
            Log.d("QuizViewModel", "Incorrect answer. Score: ${_score.value}")
        }

        val nextIndex = (currentQuestionIndex.value ?: 0) + 1
        if (nextIndex < (_totalQuestions.value ?: 10)) {
            _currentQuestionIndex.value = nextIndex
        } else {
            _showResult.value = true
        }
    }

    fun retryQuiz() {
        _score.value = 0
        _currentQuestionIndex.value = 0
        _showResult.value = false
        loadQuestions()
    }
}
