package com.example.quizsmartly.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quizsmartly.databinding.FragmentLaunchBinding

class LaunchFragment : Fragment() {

    private lateinit var binding: FragmentLaunchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLaunchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartEasy.setOnClickListener {
            navigateToQuiz("Easy")
        }

        binding.btnStartMedium.setOnClickListener {
            navigateToQuiz("Medium")
        }

        binding.btnStartHard.setOnClickListener {
            navigateToQuiz("Hard")
        }
    }

    private fun navigateToQuiz(level: String) {
        val action = LaunchFragmentDirections.actionLaunchFragmentToQuizFragment(level)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        findNavController().popBackStack()
    }
}
