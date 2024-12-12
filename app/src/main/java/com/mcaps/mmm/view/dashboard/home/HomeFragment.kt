package com.mcaps.mmm.view.dashboard.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mcaps.mmm.databinding.FragmentHomeBinding
import com.mcaps.mmm.view.ViewModelFactory
import com.mcaps.mmm.view.dashboard.test.TestViewModel
import com.mcaps.mmm.view.menu.MenuActivity

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory.getInstance(requireContext()))[HomeViewModel::class.java]
    }
    private val testViewModel: TestViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory.getInstance(requireContext()))[TestViewModel::class.java]
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var email = ""
    private var username = ""
    private var topPredictValue = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.menuButton.setOnClickListener{
            val intent = Intent(activity, MenuActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("username", username)
            intent.putExtra("topPredict", topPredictValue)
            startActivity(intent)
        }

        homeViewModel.getUserSession().observe(viewLifecycleOwner) { userModel ->
            username = userModel.username
            email = userModel.email
            binding.usernameHome.text = username
        }

        testViewModel.getMostFrequentPredictedValue()

        testViewModel.frequentPredictedValue.observe(viewLifecycleOwner) { mostFrequentValue ->
            topPredictValue = mostFrequentValue ?: "No Data"
            val topPredict = "Top Predict: $mostFrequentValue"
            binding.freqMajor.text = topPredict
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}