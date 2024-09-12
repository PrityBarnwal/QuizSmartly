package com.example.quizsmartly

import android.app.Application
import android.util.Log
import com.example.quizsmartly.roomdb.AppDatabase
import com.example.quizsmartly.roomdb.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { QuestionRepository(database.questionDao()) }

    override fun onCreate() {
        super.onCreate()
        populateDatabase()
    }

    private fun populateDatabase() {
        val questions = listOf(
            Question(
                question = "What is 2 + 2?",
                optionA = "3",
                optionB = "4",
                optionC = "5",
                optionD = "6",
                answer = "4",
                level = "easy"
            ),
            Question(
                question = "What is 5 - 3?",
                optionA = "1",
                optionB = "2",
                optionC = "3",
                optionD = "4",
                answer = "2",
                level = "easy"
            ),
            Question(
                question = "What is 3 x 3?",
                optionA = "6",
                optionB = "9",
                optionC = "12",
                optionD = "15",
                answer = "9",
                level = "easy"
            ),
            Question(
                question = "What is 12 / 4?",
                optionA = "2",
                optionB = "3",
                optionC = "4",
                optionD = "5",
                answer = "3",
                level = "easy"
            ),
            Question(
                question = "What is the color of the sky?",
                optionA = "Green",
                optionB = "Blue",
                optionC = "Red",
                optionD = "Yellow",
                answer = "Blue",
                level = "easy"
            ),
            Question(
                question = "How many days are in a week?",
                optionA = "5",
                optionB = "6",
                optionC = "7",
                optionD = "8",
                answer = "7",
                level = "easy"
            ),
            Question(
                question = "What is the capital of the USA?",
                optionA = "New York",
                optionB = "Washington, D.C.",
                optionC = "Los Angeles",
                optionD = "Chicago",
                answer = "Washington, D.C.",
                level = "easy"
            ),
            Question(
                question = "What is the shape of a circle?",
                optionA = "Triangle",
                optionB = "Square",
                optionC = "Circle",
                optionD = "Rectangle",
                answer = "Circle",
                level = "easy"
            ),
            Question(
                question = "Which animal says 'Meow'?",
                optionA = "Dog",
                optionB = "Cat",
                optionC = "Cow",
                optionD = "Sheep",
                answer = "Cat",
                level = "easy"
            ),
            Question(
                question = "How many continents are there?",
                optionA = "5",
                optionB = "6",
                optionC = "7",
                optionD = "8",
                answer = "7",
                level = "easy"
            ),
            // Add 9 more medium questions
            Question(
                question = "What is the capital of France?",
                optionA = "Berlin",
                optionB = "Madrid",
                optionC = "Paris",
                optionD = "Rome",
                answer = "Paris",
                level = "medium"
            ),
            Question(
                question = "What is the capital of France?",
                optionA = "Berlin",
                optionB = "Madrid",
                optionC = "Paris",
                optionD = "Rome",
                answer = "Paris",
                level = "medium"
            ),
            Question(
                question = "What is the largest planet in our solar system?",
                optionA = "Earth",
                optionB = "Mars",
                optionC = "Jupiter",
                optionD = "Saturn",
                answer = "Jupiter",
                level = "medium"
            ),
            Question(
                question = "Who wrote 'To Kill a Mockingbird'?",
                optionA = "Harper Lee",
                optionB = "Mark Twain",
                optionC = "J.K. Rowling",
                optionD = "Ernest Hemingway",
                answer = "Harper Lee",
                level = "medium"
            ),
            Question(
                question = "What is the chemical symbol for gold?",
                optionA = "Au",
                optionB = "Ag",
                optionC = "Pb",
                optionD = "Fe",
                answer = "Au",
                level = "medium"
            ),
            Question(
                question = "Which element has the atomic number 1?",
                optionA = "Oxygen",
                optionB = "Hydrogen",
                optionC = "Carbon",
                optionD = "Helium",
                answer = "Hydrogen",
                level = "medium"
            ),
            Question(
                question = "What year did the Titanic sink?",
                optionA = "1912",
                optionB = "1905",
                optionC = "1915",
                optionD = "1920",
                answer = "1912",
                level = "medium"
            ),
            Question(
                question = "Which organ is responsible for pumping blood throughout the body?",
                optionA = "Brain",
                optionB = "Liver",
                optionC = "Heart",
                optionD = "Lung",
                answer = "Heart",
                level = "medium"
            ),
            Question(
                question = "In which year did World War II end?",
                optionA = "1941",
                optionB = "1942",
                optionC = "1945",
                optionD = "1947",
                answer = "1945",
                level = "medium"
            ),
            Question(
                question = "What is the largest ocean on Earth?",
                optionA = "Atlantic",
                optionB = "Indian",
                optionC = "Arctic",
                optionD = "Pacific",
                answer = "Pacific",
                level = "medium"
            ),
            // Add 9 more hard questions
            Question(
                question = "What is the value of Planck's constant?",
                optionA = "6.626 × 10^-34 Js",
                optionB = "1.602 × 10^-19 C",
                optionC = "8.314 J/(mol·K)",
                optionD = "9.81 m/s^2",
                answer = "6.626 × 10^-34 Js",
                level = "hard"
            ),
            Question(
                question = "What is the theory of relativity?",
                optionA = "A theory about the nature of time and space",
                optionB = "A theory about the origin of the universe",
                optionC = "A theory about atomic structure",
                optionD = "A theory about quantum mechanics",
                answer = "A theory about the nature of time and space",
                level = "hard"
            ),
            Question(
                question = "What is the Schrödinger equation used for?",
                optionA = "Describing how quantum states evolve over time",
                optionB = "Calculating gravitational forces",
                optionC = "Predicting stock market trends",
                optionD = "Measuring electrical resistance",
                answer = "Describing how quantum states evolve over time",
                level = "hard"
            ),
            Question(
                question = "What does DNA stand for?",
                optionA = "Deoxyribonucleic Acid",
                optionB = "Ribonucleic Acid",
                optionC = "Deoxyribonucleotide Acid",
                optionD = "Ribonucleotide Acid",
                answer = "Deoxyribonucleic Acid",
                level = "hard"
            ),
            Question(
                question = "What is the Riemann Hypothesis?",
                optionA = "A conjecture about the distribution of prime numbers",
                optionB = "A theorem in algebraic geometry",
                optionC = "A theory about the nature of black holes",
                optionD = "A formula for calculating fluid dynamics",
                answer = "A conjecture about the distribution of prime numbers",
                level = "hard"
            ),
            Question(
                question = "In computer science, what does 'Big O' notation describe?",
                optionA = "The time complexity of an algorithm",
                optionB = "The speed of a processor",
                optionC = "The size of data storage",
                optionD = "The cost of a software license",
                answer = "The time complexity of an algorithm",
                level = "hard"
            ),
            Question(
                question = "What is the Heisenberg Uncertainty Principle?",
                optionA = "The principle that the position and velocity of a particle cannot both be precisely measured at the same time",
                optionB = "The principle that energy levels are quantized",
                optionC = "The principle that light behaves as a wave and a particle",
                optionD = "The principle that chemical reactions are reversible",
                answer = "The principle that the position and velocity of a particle cannot both be precisely measured at the same time",
                level = "hard"
            ),
            Question(
                question = "What does the Hubble's Law state?",
                optionA = "The universe is expanding at a rate proportional to the distance between galaxies",
                optionB = "The speed of light is constant in a vacuum",
                optionC = "Objects in motion remain in motion unless acted upon by an external force",
                optionD = "All particles are in a state of constant motion",
                answer = "The universe is expanding at a rate proportional to the distance between galaxies",
                level = "hard"
            ),
            Question(
                question = "What is the main purpose of the Large Hadron Collider?",
                optionA = "To study particle collisions and fundamental forces",
                optionB = "To measure gravitational waves",
                optionC = "To explore outer space",
                optionD = "To analyze cosmic radiation",
                answer = "To study particle collisions and fundamental forces",
                level = "hard"
            ),
            Question(
                question = "What is Gödel's incompleteness theorem?",
                optionA = "The theorem stating that in any sufficiently powerful formal system, there are statements that are true but unprovable",
                optionB = "The theorem describing the behavior of quantum particles",
                optionC = "The theorem explaining the structure of atomic nuclei",
                optionD = "The theorem predicting the future behavior of stars",
                answer = "The theorem stating that in any sufficiently powerful formal system, there are statements that are true but unprovable",
                level = "hard"
            )
        )

        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteAllQuestions()
            try {
                repository.insertAll(*questions.toTypedArray())
                Log.d("QuizApplication", "Data inserted successfully")
            } catch (e: Exception) {
                Log.e("QuizApplication", "Error inserting data: ${e.message}")
            }
        }

    }
}
