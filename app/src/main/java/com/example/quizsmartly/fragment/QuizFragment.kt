package com.example.quizsmartly.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizsmartly.QuizApplication
import com.example.quizsmartly.QuizViewModel
import com.example.quizsmartly.QuizViewModelFactory
import com.example.quizsmartly.R
import com.example.quizsmartly.databinding.DialogResultBinding
import com.example.quizsmartly.databinding.FragmentQuizBinding


class QuizFragment : Fragment() {

    private val viewModel: QuizViewModel by activityViewModels {
        QuizViewModelFactory((activity?.application as QuizApplication).repository)
    }

    private lateinit var binding: FragmentQuizBinding
    private val args: QuizFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setLevel(args.level)

        viewModel.currentQuestionIndex.observe(viewLifecycleOwner) {
            updateQuestionUI()
        }

        viewModel.questions.observe(viewLifecycleOwner) { questions ->
            updateQuestionUI()
        }


        binding.apply {
            radioGroupOptions.setOnCheckedChangeListener { _, checkedId ->
                val selectedButton = when (checkedId) {
                    radioOptionA.id -> radioOptionA
                    radioOptionB.id -> radioOptionB
                    radioOptionC.id -> radioOptionC
                    radioOptionD.id -> radioOptionD
                    else -> null
                }
                selectedButton?.let {
                    handleAnswer(it)
                }
            }
        }


        viewModel.showResult.observe(viewLifecycleOwner) { showResult ->
            if (showResult) {
                showResultDialog()
            }
        }
    }

    private fun updateQuestionUI() {
        val currentQuestionIndex = viewModel.currentQuestionIndex.value ?: 0
        val currentQuestion = viewModel.questions.value?.getOrNull(currentQuestionIndex)
        val totalQuestions = viewModel.totalQuestions.value
        binding.apply {
            if (currentQuestion != null) {
                tvQuestion.text = currentQuestion.question
                radioOptionA.text = currentQuestion.optionA
                radioOptionB.text = currentQuestion.optionB
                radioOptionC.text = currentQuestion.optionC
                radioOptionD.text = currentQuestion.optionD
                tvCounter.text = "${currentQuestionIndex + 1}/${totalQuestions}"
                resetRadioButtonColors()
                radioGroupOptions.clearCheck()
            }
        }

    }

    private fun handleAnswer(selectedButton: RadioButton) {
        val selectedOption = selectedButton.text.toString().trim()
        val correctOption = viewModel.questions.value?.get(
            viewModel.currentQuestionIndex.value ?: 0
        )?.answer?.trim()

        Log.d("QuizFragment", "Selected Option: $selectedOption")
        Log.d("QuizFragment", "Correct Option: $correctOption")

        viewModel.checkAnswer(selectedOption)

        val color = if (selectedOption == correctOption) {
            ContextCompat.getColor(requireContext(), R.color.green)
        } else {
            ContextCompat.getColor(requireContext(), R.color.red)
        }

        Log.d("QuizFragment", "Setting color for button: $color")
        selectedButton.setTextColor(color)


        binding.root.postDelayed({
            resetRadioButtonColors()
        }, 1000)
    }

    private fun resetRadioButtonColors() {
        val defaultColor = ContextCompat.getColor(requireContext(), R.color.lightPink)
        Log.d("QuizFragment", "Resetting button colors to default: $defaultColor")
        binding.radioOptionA.setTextColor(defaultColor)
        binding.radioOptionB.setTextColor(defaultColor)
        binding.radioOptionC.setTextColor(defaultColor)
        binding.radioOptionD.setTextColor(defaultColor)
    }


    private fun showResultDialog() {

        val binding = DialogResultBinding.inflate(layoutInflater)

        val dialog = Dialog(requireContext()).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(true)
            show()
        }

        binding.apply {
            ivClose.setOnClickListener {
                dialog.dismiss()
            }
            val score = viewModel.score.value ?: 0
            val totalQuestions = viewModel.totalQuestions.value ?: 10
            tvResult.text = "Your Score: $score/$totalQuestions"

            if (score >= 8) {
                tvPassFail.text = "Congratulations! You passed!"
                btnRetry.text = "Try next Level"
            } else {
                tvPassFail.text = "Sorry, you failed. Try again!"
            }

            btnRetry.setOnClickListener {
                viewModel.retryQuiz()
                dialog.dismiss()
                findNavController().navigate(R.id.launchFragment)
            }
        }
    }
}
