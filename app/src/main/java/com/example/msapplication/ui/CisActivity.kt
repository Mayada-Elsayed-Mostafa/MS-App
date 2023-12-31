package com.example.msapplication.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.msapplication.R

class CisActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cis)

        val testButton = findViewById<Button>(R.id.start_test_btn!!)
        testButton.setOnClickListener {
            val intent = Intent(this, CisTest::class.java)
            startActivity(intent)
        }

        val testByFileButton = findViewById<Button>(R.id.upload_file_btn!!)
        testByFileButton.setOnClickListener {
            val intent = Intent(this, CisTestFile::class.java)
            startActivity(intent)
        }

    }
}