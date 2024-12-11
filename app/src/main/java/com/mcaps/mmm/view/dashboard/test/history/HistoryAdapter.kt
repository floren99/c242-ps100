package com.mcaps.mmm.view.dashboard.test.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mcaps.mmm.data.local.entity.UserData
import com.mcaps.mmm.databinding.ItemHistoryviewBinding

class HistoryAdapter(private val historyList: List<UserData>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val userData = historyList[position]
        holder.bind(userData)
    }

    override fun getItemCount(): Int = historyList.size

    class HistoryViewHolder(private val binding: ItemHistoryviewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(userData: UserData) {
            binding.apply {
                historyDate.text = userData.date.toString()
                historyPrediction.text = userData.predictedValue
            }
        }
    }
}