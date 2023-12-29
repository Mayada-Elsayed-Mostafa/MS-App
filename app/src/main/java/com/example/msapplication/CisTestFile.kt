package com.example.msapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class CisTestFile : AppCompatActivity() {

    private lateinit var selectedFileTextView: TextView

    // Activity result launcher for file selection
    private val filePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    handleSelectedFile(uri)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cis_test_file)

        selectedFileTextView = findViewById(R.id.uploadedFileInfo)

        val chooseFileButton: Button = findViewById(R.id.btnChooseFile)
        chooseFileButton.setOnClickListener {
            openFilePicker()
        }

        val predictFromFileButton: Button = findViewById(R.id.predictFromFileBtn)
        predictFromFileButton.setOnClickListener {
            // Perform prediction or further processing using the selected file data
            // Add your logic here
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*" // Allow all file types
        filePickerLauncher.launch(intent)
    }

    private fun handleSelectedFile(uri: Uri) {
        // Read the contents of the selected file and update UI
        val inputStream = contentResolver.openInputStream(uri)
        val content = inputStream?.bufferedReader().use { it?.readText() }
        selectedFileTextView.text = "File chosen: $uri\nContent: $content"
    }
}
