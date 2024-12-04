package com.mcaps.mmm.view.dashboard.path

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mcaps.mmm.data.api.response.MajorDataItem
import com.mcaps.mmm.databinding.ItemGridPathBinding
import com.mcaps.mmm.view.dashboard.path.PathAdapter.MyViewHolder.Companion.DIFF_CALLBACK

class PathAdapter(private val context: Context) : ListAdapter<MajorDataItem, PathAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            ItemGridPathBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val path = getItem(position)
        if (path != null) {
            holder.bind(path)
            holder.itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("name", path.title)
                    putExtra("description", path.description)
                }
                context.startActivity(intent)
            }
        }
    }

    class MyViewHolder(private val binding: ItemGridPathBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(path: MajorDataItem) {
            binding.tvItemName.text = path.title
            binding.tvItemDescription.text = path.description
            Log.d("PathAdapter", "Binding story: ${path.title}")
        }

        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MajorDataItem>() {
                override fun areItemsTheSame(
                    oldItem: MajorDataItem,
                    newItem: MajorDataItem
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: MajorDataItem,
                    newItem: MajorDataItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}