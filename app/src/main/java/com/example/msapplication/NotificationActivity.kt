package com.example.msapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

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

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val eventDatabaseHelper = EventDatabaseHelper(this)

        // Get the current date in the desired format (e.g., "yyyy-MM-d")
        val currentDate = SimpleDateFormat("yyyy-MM-d").format(Date())

        // Retrieve events for the current date
        val events = eventDatabaseHelper.getEventsForDate(currentDate) // Use the database helper method

        Log.d("NotificationActivity", "Retrieved ${events.size} events for date: $currentDate")

        val adapter = EventAdapter(events)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Find the TextView and ImageView views
        val noNotificationTv = findViewById<TextView>(R.id.noDocuments_tv)
        val noNotificationImg = findViewById<ImageView>(R.id.noDocuments_img)

        // Check if events were retrieved and set the visibility of the TextView and ImageView
        if (events.isEmpty()) {
            noNotificationTv.visibility = View.VISIBLE
            noNotificationImg.visibility = View.VISIBLE
        } else {
            noNotificationTv.visibility = View.GONE
            noNotificationImg.visibility = View.GONE
        }
    }
}
