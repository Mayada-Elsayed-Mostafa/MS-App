package com.example.msapplication.ui

import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.msapplication.R
import com.google.android.material.textfield.TextInputLayout
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class CisTest : AppCompatActivity() {

    private lateinit var genderInputLayout: TextInputLayout
    private lateinit var gender: AutoCompleteTextView

    private lateinit var ageInputLayout: TextInputLayout
    private lateinit var age: AutoCompleteTextView

    private lateinit var timeInputLayout: TextInputLayout
    private lateinit var time: AutoCompleteTextView

    private lateinit var breastfeedingInputLayout: TextInputLayout
    private lateinit var breastfeeding: AutoCompleteTextView

    private lateinit var varicellaInputLayout: TextInputLayout
    private lateinit var varicella: AutoCompleteTextView

    private lateinit var initial_symptomInputLayout: TextInputLayout
    private lateinit var initial_symptom: AutoCompleteTextView

    private lateinit var mono_or_poly_symptomaticInputLayout: TextInputLayout
    private lateinit var mono_or_poly_symptomatic: AutoCompleteTextView

    private lateinit var oligoclonal_BandsInputLayout: TextInputLayout
    private lateinit var oligoclonal_Bands: AutoCompleteTextView

    private lateinit var llssepInputLayout: TextInputLayout
    private lateinit var llssep: AutoCompleteTextView

    private lateinit var ulssepInputLayout: TextInputLayout
    private lateinit var ulssep: AutoCompleteTextView

    private lateinit var verInputLayout: TextInputLayout
    private lateinit var ver: AutoCompleteTextView

    private lateinit var baepInputLayout: TextInputLayout
    private lateinit var baep: AutoCompleteTextView

    private lateinit var periventricular_MRIInputLayout: TextInputLayout
    private lateinit var periventricular_MRI: AutoCompleteTextView

    private lateinit var cortical_MRIInputLayout: TextInputLayout
    private lateinit var cortical_MRI: AutoCompleteTextView

    private lateinit var infratentorial_MRIInputLayout: TextInputLayout
    private lateinit var infratentorial_MRI: AutoCompleteTextView

    private lateinit var spinal_Cord_MRIInputLayout: TextInputLayout
    private lateinit var spinal_Cord_MRI: AutoCompleteTextView

    private lateinit var initial_EDSSInputLayout: TextInputLayout
    private lateinit var initial_EDSS: AutoCompleteTextView

    private lateinit var final_EDSSInputLayout: TextInputLayout
    private lateinit var final_EDSS: AutoCompleteTextView


    private lateinit var predictionTextView: TextView
    private lateinit var predictButton: Button
    private lateinit var tflite: Interpreter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cis_test)

        // Initialize the AutoCompleteTextViews and other elements
        genderInputLayout = findViewById(R.id.genderInputLayout)
        gender = findViewById(R.id.gender)

        ageInputLayout = findViewById(R.id.ageInputLayout)
        age = findViewById(R.id.age)

        timeInputLayout = findViewById(R.id.timeInputLayout)
        time = findViewById(R.id.time)

        breastfeedingInputLayout = findViewById(R.id.breastfeedingInputLayout)
        breastfeeding = findViewById(R.id.breastfeeding)

        varicellaInputLayout = findViewById(R.id.varicellaInputLayout)
        varicella = findViewById(R.id.varicella)

        initial_symptomInputLayout = findViewById(R.id.initial_symptomInputLayout)
        initial_symptom = findViewById(R.id.initial_symptom)

        mono_or_poly_symptomaticInputLayout = findViewById(R.id.mono_or_poly_symptomaticInputLayout)
        mono_or_poly_symptomatic = findViewById(R.id.mono_or_poly_symptomatic)

        oligoclonal_BandsInputLayout = findViewById(R.id.oligoclonal_BandsInputLayout)
        oligoclonal_Bands = findViewById(R.id.oligoclonal_Bands)

        llssepInputLayout = findViewById(R.id.llssepInputLayout)
        llssep = findViewById(R.id.llssep)

        ulssepInputLayout = findViewById(R.id.ulssepInputLayout)
        ulssep = findViewById(R.id.ulssep)

        verInputLayout = findViewById(R.id.verInputLayout)
        ver = findViewById(R.id.ver)

        baepInputLayout = findViewById(R.id.baepInputLayout)
        baep = findViewById(R.id.baep)

        periventricular_MRIInputLayout = findViewById(R.id.periventricular_MRIInputLayout!!)
        periventricular_MRI = findViewById(R.id.periventricular_MRI)

        cortical_MRIInputLayout = findViewById(R.id.cortical_MRIInputLayout)
        cortical_MRI =findViewById(R.id.cortical_MRI)

        infratentorial_MRIInputLayout = findViewById(R.id.infratentorial_MRIInputLayout!!)
        infratentorial_MRI =findViewById(R.id.infratentorial_MRI!!)

        spinal_Cord_MRIInputLayout = findViewById(R.id.spinal_Cord_MRIInputLayout!!)
        spinal_Cord_MRI = findViewById(R.id.spinal_Cord_MRI)

        initial_EDSSInputLayout = findViewById(R.id.initial_EDSSInputLayout)
        initial_EDSS = findViewById(R.id.initial_EDSS)

        final_EDSSInputLayout = findViewById(R.id.final_EDSSInputLayout)
        final_EDSS = findViewById(R.id.final_EDSS)


        predictionTextView = findViewById(R.id.predictionTextView)

        predictButton = findViewById(R.id.predict_btn)

        // Load the TFLite model from the assets folder
        try {
            tflite = Interpreter(loadModelFile())
        } catch (e: IOException) {
            e.printStackTrace()
        }

        predictButton.setOnClickListener(View.OnClickListener {
            // Get user input values
            val gender = gender.text.toString().toFloat()
            val age = age.text.toString().toFloat()
            val time = time.text.toString().toFloat()
            val breastfeeding = breastfeeding.text.toString().toFloat()
            val varicella = varicella.text.toString().toFloat()
            val initial_symptom = initial_symptom.text.toString().toFloat()
            val mono_or_poly_symptomatic = mono_or_poly_symptomatic.text.toString().toFloat()
            val oligoclonal_Bands = oligoclonal_Bands.text.toString().toFloat()
            val llssep = llssep.text.toString().toFloat()
            val ulssep = ulssep.text.toString().toFloat()
            val ver = ver.text.toString().toFloat()
            val baep = baep.text.toString().toFloat()
            val periventricular_MRI = periventricular_MRI.text.toString().toFloat()
            val cortical_MRI = cortical_MRI.text.toString().toFloat()
            val infratentorial_MRI = infratentorial_MRI.text.toString().toFloat()
            val spinal_Cord_MRI = spinal_Cord_MRI.text.toString().toFloat()
            val initial_EDSS = initial_EDSS.text.toString().toFloat()
            val final_EDSS = final_EDSS.text.toString().toFloat()

            // Get other input values here

            // Perform prediction using the loaded model
            val prediction = makePrediction(gender, age, time, breastfeeding, varicella, initial_symptom, mono_or_poly_symptomatic, oligoclonal_Bands, llssep, ulssep, ver, baep, periventricular_MRI, cortical_MRI, infratentorial_MRI, spinal_Cord_MRI, initial_EDSS, final_EDSS)

            // Display the prediction
            predictionTextView.text = "$prediction"
        })
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

    private fun makePrediction(
        gender: Float,
        age: Float,
        time: Float,
        breastfeeding: Float,
        varicella: Float,
        initial_symptom: Float,
        mono_or_poly_symptomatic: Float,
        oligoclonal_Bands: Float,
        llssep: Float,
        ulssep: Float,
        ver: Float,
        baep: Float,
        periventricular_MRI: Float,
        cortical_MRI: Float,
        infratentorial_MRI: Float,
        spinal_Cord_MRI: Float,
        initial_EDSS: Float,
        final_EDSS: Float
    ): String {
        val inputs = arrayOf(floatArrayOf(gender, age, time,breastfeeding, varicella, initial_symptom, mono_or_poly_symptomatic, oligoclonal_Bands, llssep, ulssep, ver, baep, periventricular_MRI, cortical_MRI, infratentorial_MRI, spinal_Cord_MRI, initial_EDSS, final_EDSS)) // Modify this based on your model input shape
        val outputs = Array(1) { FloatArray(1) }
        tflite.run(inputs, outputs)

        val prediction = outputs[0][0]

        // Determine the group based on the prediction
        return if (prediction < 1) { "The User belongs to Group 1" } else { "The User belongs to Group 2" }
    }
}