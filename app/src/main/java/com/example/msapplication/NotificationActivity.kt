package com.example.msapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val eventDatabaseHelper = EventDatabaseHelper(this)

        // Replace 'date' with the specific date you want to retrieve events for
        val date = "2023-11-06" // Example date

        val events = eventDatabaseHelper.getEventsForDate(date)
        val adapter = EventAdapter(events)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
