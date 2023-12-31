package com.example.msapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.msapplication.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        // Reference the Toolbar from the layout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = ""

        // Set the Toolbar as the support action bar
        setSupportActionBar(toolbar)

        // Enable the back button (up button) in the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Handle the back button click event
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}