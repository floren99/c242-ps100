package com.mcaps.mmm.view.dashboard.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mcaps.mmm.databinding.FragmentHomeBinding
import com.mcaps.mmm.view.auth.login.LoginActivity
import com.mcaps.mmm.view.menu.MenuActivity
import com.mcaps.mmm.view.question.QuestionActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.menuButton.setOnClickListener{
            val intent = Intent(activity, MenuActivity::class.java)
            startActivity(intent)
        }
        binding.signupHome.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}