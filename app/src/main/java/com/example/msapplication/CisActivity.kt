package com.example.msapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CisActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cis)

        val testButton = findViewById<Button>(R.id.do_a_cis_test_btn!!)
        testButton.setOnClickListener {
            val intent = Intent(this, CisTest::class.java)
            startActivity(intent)
        }

    }
}