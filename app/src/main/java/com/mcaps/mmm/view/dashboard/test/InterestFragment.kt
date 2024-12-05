package com.mcaps.mmm.view.dashboard.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.fragment.app.activityViewModels
import com.mcaps.mmm.R
import com.mcaps.mmm.databinding.FragmentInterestBinding

class InterestFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentInterestBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val sharedViewModel: TestViewModel by activityViewModels()

    // List of questions
    private val questions = listOf(
        "1. Do you enjoy problem-solving?",
        "2. Do you like working in teams?",
        "3. Do you prefer hands-on tasks?",
        "4. Do you enjoy learning new technologies?",
        "5. Do you feel comfortable speaking in public?",
        "6. Are you detail-oriented?",
        "7. Do you like analyzing data?"
    )

    // Answers storage; -1 indicates unanswered
    private val answers = MutableList(questions.size) { -1 }
    private var currentQuestionIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInterestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateQuestion()

        binding.prevButton.setOnClickListener {
            saveAnswer()
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--
                updateQuestion()
            }
        }

        binding.nextButton.setOnClickListener {
            saveAnswer()
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                updateQuestion()
            } else {
                // Save answers in ViewModel and dismiss
                sharedViewModel.updateMinat(answers)
                dismiss()
            }
        }
    }

    private fun updateQuestion() {
        binding.questionTextView.text = questions[currentQuestionIndex]

        // Reset RadioGroup and restore the saved answer
        binding.answerRadioGroup.clearCheck()
        when (answers[currentQuestionIndex]) {
            0 -> binding.radioNo.isChecked = true
            1 -> binding.radioYes.isChecked = true
        }

        // Update button states
        binding.prevButton.isEnabled = currentQuestionIndex > 0
        binding.nextButton.text = if (currentQuestionIndex == questions.size - 1) "Submit" else "Next"
    }

    private fun saveAnswer() {
        val selectedOption = when (binding.answerRadioGroup.checkedRadioButtonId) {
            R.id.radioNo -> 0
            R.id.radioYes -> 1
            else -> -1
        }
        if (selectedOption != -1) {
            answers[currentQuestionIndex] = selectedOption
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
