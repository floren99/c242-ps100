package com.mcaps.mmm.view.question.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mcaps.mmm.R
import com.mcaps.mmm.databinding.ItemMarkBinding

class MarksAdapter(
    private val questionsCount: Int,
    private val answeredQuestions: List<Boolean>,
    private val onQuestionClick: (Int) -> Unit
) : RecyclerView.Adapter<MarksAdapter.MarkViewHolder>() {

    inner class MarkViewHolder(private val binding: ItemMarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.tvQuestionNumber.text = (position + 1).toString()

            // Highlight untuk soal yang sudah dijawab
            val colorRes = if (answeredQuestions[position]) {
                R.color.teal_700 // Warna hijau untuk jawaban yang telah dijawab
            } else {
                R.color.gray // Warna abu-abu untuk soal yang belum dijawab
            }
            binding.tvQuestionNumber.setBackgroundResource(colorRes)

            // Klik untuk memilih soal
            binding.root.setOnClickListener {
                onQuestionClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkViewHolder {
        val binding = ItemMarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarkViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = questionsCount
}
