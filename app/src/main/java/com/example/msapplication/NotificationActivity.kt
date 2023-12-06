package com.example.msapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        class NotificationActivity : AppCompatActivity() {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_notification)

                val toolbar = findViewById<Toolbar>(R.id.toolbar)
                toolbar.title = ""
                setSupportActionBar(toolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar.setNavigationOnClickListener {
                    onBackPressed()
                }

                val recyclerView = findViewById<RecyclerView>(R.id.notificationRecyclerView!!)
                val eventDatabaseHelper = EventDatabaseHelper(this)
                val currentDate = SimpleDateFormat("yyyy-MM-d").format(Date())
                val events = eventDatabaseHelper.getEventsForDate(currentDate)

                Log.d(
                    "NotificationActivity",
                    "Retrieved ${events.size} events for date: $currentDate"
                )

                val adapter = EventAdapter(events)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = adapter

                val noDocumentsTv = findViewById<TextView>(R.id.noDocumentsTextView!!)
                val noDocumentsImg = findViewById<ImageView>(R.id.noDocumentsImageView!!)

                if (events.isEmpty()) {
                    noDocumentsTv.visibility = View.VISIBLE
                    noDocumentsImg.visibility = View.VISIBLE
                } else {
                    noDocumentsTv.visibility = View.GONE
                    noDocumentsImg.visibility = View.GONE
                }
            }
        }
    }
}
