package com.example.msapplication.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.msapplication.R
import org.tensorflow.lite.Interpreter
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

class RiskActivity : AppCompatActivity() {

    private val PICK_CSV_FILE_REQUEST = 1
    private lateinit var modelInterpreter: Interpreter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_risk)

        // Load the TensorFlow Lite model when the activity is created
        modelInterpreter = Interpreter(loadModelFileFromAssets())

        findViewById<Button>(R.id.btnChooseFile).setOnClickListener {
            pickCsvFile()
        }

        findViewById<Button>(R.id.predictFromFileBtn).setOnClickListener {
            // Placeholder for direct prediction from file
            // For now, let's just display a message
            val resultTextView = findViewById<TextView>(R.id.result_tv)
            resultTextView.text = "Prediction will be displayed here"
        }
    }

    private fun loadModelFileFromAssets(): ByteBuffer {
        val modelFileDescriptor = assets.openFd("risk_model.tflite")
        val inputStream = FileInputStream(modelFileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = modelFileDescriptor.startOffset
        val declaredLength = modelFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun pickCsvFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "text/csv"
        startActivityForResult(intent, PICK_CSV_FILE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_CSV_FILE_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedFileUri = data?.data
            if (selectedFileUri != null) {
                processCsvFile(selectedFileUri)
            }
        }
    }

    private fun processCsvFile(fileUri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(fileUri)
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val columns = line?.split(",") // Assuming CSV columns are separated by commas
                if (columns != null) {
                    val inputData = extractDataFromCsv(columns)
                    val prediction = predict(inputData)
                    // Display or use the prediction result as needed
                    val resultTextView = findViewById<TextView>(R.id.result_tv)
                    resultTextView.text = "Prediction: $prediction"
                }
            }
            inputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun extractDataFromCsv(columns: List<String>): FloatArray {
        // Extract data from CSV columns and convert them to the format required for prediction
        // Example: Convert strings to floats, handle missing values, etc.
        // For demonstration purpose, just converting strings to floats
        val inputData = FloatArray(columns.size)
        for (i in columns.indices) {
            inputData[i] = columns[i].toFloatOrNull() ?: 0f // If conversion fails, assign 0
        }
        return inputData
    }

    private fun predict(inputData: FloatArray): Float {
        val output = Array(1) { FloatArray(1) }
        modelInterpreter.run(inputData, output)
        return output[0][0]
    }
}
