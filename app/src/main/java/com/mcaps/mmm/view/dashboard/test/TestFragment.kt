package com.mcaps.mmm.view.dashboard.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mcaps.mmm.databinding.FragmentTestBinding
import com.mcaps.mmm.view.auth.register.RegisterFragment

class TestFragment : Fragment() {
    private val sharedViewModel: TestViewModel by activityViewModels()

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.scores.observe(viewLifecycleOwner) { scores ->
            binding.textTestHasil.text = scores.entries.joinToString(", ") {
                "${it.key.capitalize()}: ${it.value}"
            }
        }
        sharedViewModel.minat.observe(viewLifecycleOwner) { minat ->
            binding.textTestMinat.text = minat.joinToString(", "){
                "${it}"
            }
        }

        sharedViewModel.answers.let { answers ->
            answers.forEachIndexed { index, answer ->
                println("Question ${index + 1}: Answer $answer")
            }
        }

        binding.buttonInputNilai.setOnClickListener{
            val scoreFragment = ScoreFragment()
            scoreFragment.show(parentFragmentManager, "ScoreFragment")
        }
        binding.buttonInputMinat.setOnClickListener{
            val interestFragment = InterestFragment()
            interestFragment.show(parentFragmentManager, "InterestFragment")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}