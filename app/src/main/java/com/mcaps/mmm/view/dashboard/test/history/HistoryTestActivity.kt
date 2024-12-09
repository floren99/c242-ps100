package com.mcaps.mmm.view.dashboard.test.history

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mcaps.mmm.R
import com.mcaps.mmm.data.pref.TestPreference
import com.mcaps.mmm.data.repository.PredictRepository
import com.mcaps.mmm.data.repository.UserDataRepository
import com.mcaps.mmm.databinding.ActivityHistoryTestBinding
import com.mcaps.mmm.view.MainViewModel
import com.mcaps.mmm.view.ViewModelFactory
import com.mcaps.mmm.view.dashboard.test.TestViewModel

class HistoryTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryTestBinding
    private lateinit var historyAdapter: HistoryAdapter
    private val testViewModel: TestViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory.getInstance(this))[TestViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvHistory.layoutManager = LinearLayoutManager(this)

        testViewModel.userHistory.observe(this, Observer { userDataList ->
            userDataList?.let {
                historyAdapter = HistoryAdapter(it)
                binding.rvHistory.adapter = historyAdapter
            }
        })
    }
}