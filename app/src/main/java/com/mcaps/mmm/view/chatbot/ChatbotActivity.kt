package com.mcaps.mmm.view.chatbot

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mcaps.mmm.view.chatbot.adapter.GeminiAdapter
import com.mcaps.mmm.view.chatbot.model.DataResponse
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.mcaps.mmm.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.PickVisualMediaRequest
import android.util.Log

class ChatbotActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var image: ImageView
    private lateinit var clearImageButton: ImageView
    private lateinit var recyclerView: RecyclerView
    private var bitmap: Bitmap? = null
    private var imageUri: String = ""
    private var responseData = arrayListOf<DataResponse>()
    lateinit var adapter: GeminiAdapter

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                imageUri = uri.toString()
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                image.setImageURI(uri)
                clearImageButton.visibility = ImageView.VISIBLE
            } else {
                Log.d("Photopicker", "no media selected")
            }
        }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.app_bar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        editText = findViewById(R.id.ask_edit_text)
        button = findViewById(R.id.ask_button)
        image = findViewById(R.id.select_iv)
        clearImageButton = findViewById(R.id.clear_image_button)
        recyclerView = findViewById(R.id.recycler_view_id)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)  // Set reverseLayout to true
        adapter = GeminiAdapter(this, responseData, recyclerView)
        recyclerView.adapter = adapter

        adapter.notifyDataSetChanged()

        image.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        clearImageButton.setOnClickListener {
            clearSelectedImage()
        }

        button.setOnClickListener {
            sendMessage()
        }

        editText.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                sendMessage()
                true
            } else {
                false
            }
        }
    }

    private fun sendMessage() {
        if (editText.text.isNotBlank()) {
            val generativeModel = GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = getString(R.string.api_key)
            )

            val userPrompt = editText.text.toString()
            editText.setText("")

            val userImageUri = if (imageUri.isBlank()) "" else imageUri

            // Tambahkan pesan user
            val userMessage = DataResponse(0, userPrompt, imageUri = userImageUri)
            responseData.add(0, userMessage)
            adapter.notifyItemInserted(0)

            recyclerView.post { recyclerView.scrollToPosition(0) }

            // Tambahkan loading indicator
            val loadingMessage = DataResponse(1, "...", imageUri = "", isLoading = true)
            responseData.add(0, loadingMessage)
            adapter.notifyItemInserted(0)
            recyclerView.post { recyclerView.scrollToPosition(0) }

            // Simulasi input ke AI
            val inputContent = content {
                if (bitmap != null) image(bitmap!!)
                text(userPrompt)
            }

            GlobalScope.launch {
                val response = generativeModel.generateContent(inputContent)

                runOnUiThread {
                    // Hapus indikator loading
                    responseData.removeAt(0)
                    adapter.notifyItemRemoved(0)

                    // Tambahkan respons dari AI
                    val aiResponse = DataResponse(1, response.text ?: "", "")
                    responseData.add(0, aiResponse)
                    adapter.notifyItemInserted(0)
                    recyclerView.post { recyclerView.scrollToPosition(0) }
                }
            }
        } else {
            Toast.makeText(this, "Please enter a message before sending", Toast.LENGTH_SHORT).show()
        }
    }


    private fun clearSelectedImage() {
        imageUri = ""
        bitmap = null
        image.setImageResource(R.drawable.ic_place_holder)
        clearImageButton.visibility = ImageView.GONE
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.delete_chat -> {
                responseData.clear()
                adapter.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chatbot, menu)
        return true
    }
}
