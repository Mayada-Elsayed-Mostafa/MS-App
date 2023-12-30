package com.example.msapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class CisTestFile : AppCompatActivity() {

    private lateinit var selectedFileTextView: TextView
    private lateinit var resultTextView: TextView
    private lateinit var tflite: Interpreter
    private var selectedFileUri: Uri? = null

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
        resultTextView = findViewById(R.id.result_tv)

        val chooseFileButton: Button = findViewById(R.id.btnChooseFile)
        chooseFileButton.setOnClickListener {
            openFilePicker()
        }

        try {
            tflite = Interpreter(loadModelFile())
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val predictFromFileButton: Button = findViewById(R.id.predictFromFileBtn)
        predictFromFileButton.setOnClickListener {
            // Check if the TensorFlow Lite model is loaded
            if (::tflite.isInitialized) {
                // Perform prediction or further processing using the selected file data
                val result = performPrediction()
                resultTextView.text = result
            } else {
                resultTextView.text = "Model not loaded. Unable to make predictions."
            }
        }
    }

    private fun performPrediction(): String {
        // Check if the selected file URI is available
        val uri = selectedFileUri
        if (uri == null) {
            return "No file selected."
        }

        try {
            // Handle the selected file data
            val inputStream = contentResolver.openInputStream(uri)
            val content = inputStream?.bufferedReader().use { it?.readText() }

            // Parse the content into floating-point numbers
            val values = content?.split(",")?.mapNotNull { it.toFloatOrNull() }

            if (values != null && values.size == 18) {
                // Call the makePrediction function with the parsed values
                return makePrediction(*values.toFloatArray())
            } else {
                return "Invalid file format or content."
            }
        } catch (e: Exception) {
            // Handle exceptions (e.g., IOException, NumberFormatException) appropriately
            return "Error processing file: ${e.message}"
        }
    }

    @Throws(IOException::class)
    private fun loadModelFile(): MappedByteBuffer {
        val assetFileDescriptor = assets.openFd("logistic_regression_cis_model.tflite")
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*" // Allow all file types
        filePickerLauncher.launch(intent)
    }

    private fun makePrediction(vararg values: Float): String {
        // Ensure the correct number of input values
        if (values.size != 18) {
            return "Invalid number of input values."
        }

        // Prepare the input array
        val inputs = arrayOf(values)

        // Output array to store predictions
        val outputs = Array(1) { FloatArray(1) }

        // Run the TensorFlow Lite model
        tflite.run(inputs, outputs)

        // Get the prediction from the output array
        val prediction = outputs[0][0]

        // Determine the group based on the prediction
        return if (prediction < 1) {
            "The User belongs to Group 1"
        } else {
            "The User belongs to Group 2"
        }
    }

    private fun handleSelectedFile(uri: Uri) {
        selectedFileUri = uri
        // Update UI or perform any further actions related to the selected file
        selectedFileTextView.text = "File chosen: $uri"
    }
}