package com.example.msapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

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

        // Check if events were retrieved and set the visibility of the TextView
        if (events.isEmpty()) {
            findViewById<TextView>(R.id.noNotification_tv).visibility = View.VISIBLE
        } else {
            findViewById<TextView>(R.id.noNotification_tv).visibility = View.GONE
        }
    }
}