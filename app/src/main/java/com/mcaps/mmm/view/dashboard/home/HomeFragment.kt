package com.mcaps.mmm.view.dashboard.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mcaps.mmm.databinding.FragmentHomeBinding
import com.mcaps.mmm.view.MainViewModel
import com.mcaps.mmm.view.ViewModelFactory
import com.mcaps.mmm.view.auth.login.LoginActivity
import com.mcaps.mmm.view.auth.login.LoginViewModel
import com.mcaps.mmm.view.dashboard.test.TestViewModel
import com.mcaps.mmm.view.menu.MenuActivity
import com.mcaps.mmm.view.question.QuestionActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory.getInstance(requireContext()))[HomeViewModel::class.java]
    }
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var email = ""
    private var username = ""

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
            startActivity(intent)
        }

        homeViewModel.getUserSession().observe(viewLifecycleOwner) { userModel ->
            username = userModel.username
            email = userModel.email
            binding.usernameHome.text = username
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}