package com.mcaps.mmm.view.question

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.mcaps.mmm.R

class RulesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rules, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton: Button = view.findViewById(R.id.btnCancel)
        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        val lanjutkanButton: Button = view.findViewById(R.id.btnStart)
        lanjutkanButton.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun showConfirmationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirmation, null)

        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogView)

        val btnStart: Button = dialogView.findViewById(R.id.btnStart)
        val btnCancel: Button = dialogView.findViewById(R.id.btnCancel)
        val dialogMessage: TextView = dialogView.findViewById(R.id.dialog_message)
        val dialogTitle: TextView = dialogView.findViewById(R.id.dialog_title)
        val dialogIcon: ImageView = dialogView.findViewById(R.id.dialog_icon)
        val progressBar: ProgressBar = dialogView.findViewById(R.id.progress_bar)

        dialogMessage.text = "Are you sure you want to start the test?"
        dialogTitle.text = "Start Test"

        val dialog = builder.create()

        btnStart.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            btnStart.isEnabled = false
            btnCancel.isEnabled = false

            Handler(Looper.getMainLooper()).postDelayed({
                progressBar.visibility = View.GONE
                startTest()
                dialog.dismiss()
            }, 2000)
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(true)
        dialog.show()
    }

    private fun startTest() {
        val intent = Intent(requireContext(), QuizActivity::class.java)
        startActivity(intent)
    }
}
