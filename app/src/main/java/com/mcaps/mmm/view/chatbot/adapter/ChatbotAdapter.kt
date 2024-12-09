package com.mcaps.mmm.view.chatbot.adapter

import android.content.Context
import android.net.Uri
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mcaps.mmm.view.chatbot.customview.TypeWriter
import com.mcaps.mmm.view.chatbot.model.DataResponse
import com.bumptech.glide.Glide
import com.mcaps.mmm.R

class GeminiAdapter(var context: Context, var list: ArrayList<DataResponse>,
                    val recyclerView: RecyclerView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val GEMINI = 1
        const val USER = 0
    }

    private inner class GeminiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TypeWriter = itemView.findViewById(R.id.tv_gemini_response)
        val image: ImageView = itemView.findViewById(R.id.iv_gemini_response)

        fun bind(position: Int) {
            val data = list[position]

            if (data.isLoading) {
                // Tampilkan animasi loading dengan titik-titik berjalan
                animateLoadingDots()
                image.visibility = View.GONE
            } else {
                val formattedText = processBoldText(data.prompt)

                if (list.size - 1 == position) {
                    text.animateText(formattedText)
                    text.setCharacterDelay(30)
                } else {
                    text.text = formattedText
                }

                if (data.imageUri.isNotBlank()) {
                    image.visibility = View.VISIBLE
                    Glide.with(context)
                        .load(Uri.parse(data.imageUri))
                        .into(image)
                } else {
                    image.visibility = View.GONE
                }
            }
        }

        private fun animateLoadingDots() {
            text.text = "....."
            val handler = android.os.Handler()
            var dots = 0
            handler.postDelayed(object : Runnable {
                override fun run() {
                    dots = (dots + 1) % 6
                    val dotString = ".".repeat(dots)
                    text.text = "$dotString"
                    handler.postDelayed(this, 500)
                }
            }, 500)
        }



        private fun processBoldText(input: String): SpannableString {
            val spannableString = SpannableString(input)
            var startIndex = input.indexOf("**")
            while (startIndex != -1) {
                val endIndex = input.indexOf("**", startIndex + 2)
                if (endIndex != -1) {
                    spannableString.setSpan(
                        StyleSpan(android.graphics.Typeface.BOLD),
                        startIndex + 2,
                        endIndex,
                        0
                    )
                    startIndex = input.indexOf("**", endIndex + 2)
                } else {
                    break
                }
            }
            return spannableString
        }
    }

    private inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: TextView = itemView.findViewById(R.id.tv_user_response)
        var image: ImageView = itemView.findViewById(R.id.iv_user_res)

        fun bind(position: Int) {
            val data = list[position]
            text.text = data.prompt

            if (data.imageUri.isNotBlank()) {
                image.visibility = View.VISIBLE
                Glide.with(context)
                    .load(Uri.parse(data.imageUri))
                    .into(image)
            } else {
                image.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == GEMINI) {
            GeminiViewHolder(LayoutInflater.from(context).inflate(R.layout.gemini_layout, parent, false))
        } else {
            UserViewHolder(LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].isUser
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position].isUser == GEMINI) {
            (holder as GeminiViewHolder).bind(position)
        } else {
            (holder as UserViewHolder).bind(position)
        }
    }

    fun addItem(data: DataResponse) {
        list.add(data)
        notifyItemInserted(list.size - 1)
        recyclerView.scrollToPosition(list.size - 1)
    }
    fun addItemFromTop(data: DataResponse) {
        list.add(0, data)
        notifyItemInserted(0)
        recyclerView.scrollToPosition(0)
    }
}
