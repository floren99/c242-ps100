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
        // Inflate the fragment layout
        return inflater.inflate(R.layout.fragment_rules, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle the Back button click
        val backButton: Button = view.findViewById(R.id.btnCancel)
        backButton.setOnClickListener {
            // Navigate back to the previous screen (QuestionActivity)
            activity?.onBackPressed()
        }

        // Handle the Lanjutkan (Continue) button click
        val lanjutkanButton: Button = view.findViewById(R.id.btnStart)
        lanjutkanButton.setOnClickListener {
            // Show the custom pop-up dialog with Start and Cancel buttons
            showConfirmationDialog()
        }
    }

    private fun showConfirmationDialog() {
        // Inflate the custom layout for the dialog
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirmation, null)

        // Create the AlertDialog builder
        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogView)

        // Access buttons and other elements from the custom layout
        val btnStart: Button = dialogView.findViewById(R.id.btnStart)
        val btnCancel: Button = dialogView.findViewById(R.id.btnCancel)
        val dialogMessage: TextView = dialogView.findViewById(R.id.dialog_message)
        val dialogTitle: TextView = dialogView.findViewById(R.id.dialog_title)
        val dialogIcon: ImageView = dialogView.findViewById(R.id.dialog_icon)
        val progressBar: ProgressBar = dialogView.findViewById(R.id.progress_bar)

        // Set message if needed (dynamic content)
        dialogMessage.text = "Are you sure you want to start the test?"
        dialogTitle.text = "Start Test"  // Set dynamic title

        // Create the dialog instance after accessing all the views
        val dialog = builder.create()

        // Define the "Start" button action
        btnStart.setOnClickListener {
            // Show the loading progress bar and disable the buttons
            progressBar.visibility = View.VISIBLE
            btnStart.isEnabled = false
            btnCancel.isEnabled = false

            // Simulate a delay before starting the test (e.g., network call, loading)
            Handler(Looper.getMainLooper()).postDelayed({
                // Hide the progress bar and proceed to start the test
                progressBar.visibility = View.GONE
                startTest() // Proceed to start the test
                dialog.dismiss() // Dismiss the dialog
            }, 2000) // Delay for 2 seconds (you can adjust the duration as needed)
        }

        // Define the "Cancel" button action
        btnCancel.setOnClickListener {
            // Action when the "Cancel" button is clicked
            dialog.dismiss() // Dismiss the dialog when cancel is clicked
        }

        // Optionally, set additional dialog properties (e.g., cancelable)
        dialog.setCancelable(true)  // Allow dialog to be cancelled if needed

        // Show the dialog
        dialog.show()
    }

    private fun startTest() {
        // Start the quiz or navigate to the quiz activity
        // You can implement your logic here to start the quiz.
        // For example, you can navigate to the next activity or fragment.
        // Example of navigating to another activity:
        val intent = Intent(requireContext(), QuizActivity::class.java)
        startActivity(intent)
    }
}
