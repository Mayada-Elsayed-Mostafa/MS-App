package com.example.msapplication

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarFragment : Fragment() {

    private lateinit var eventNameEditText: TextInputEditText
    private lateinit var eventDetailsEditText: TextInputEditText
    private lateinit var dateTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var saveEventButton: Button
    private lateinit var calendarView: CalendarView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        eventNameEditText = view.findViewById(R.id.name)
        eventDetailsEditText = view.findViewById(R.id.details)
        dateTextView = view.findViewById(R.id.tv_date)
        timeTextView = view.findViewById(R.id.tv_time)
        saveEventButton = view.findViewById(R.id.save_event_btn)

        // Set OnClickListener for dateTextView
        dateTextView.setOnClickListener {
            showDatePickerDialog()
        }

        saveEventButton.setOnClickListener {
            saveEvent()
        }

        return view
    }

    private fun showDatePickerDialog() {
        // Initialize CalendarView
        calendarView = CalendarView(requireContext())

        // Create an AlertDialog with CalendarView
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle("Select Date")
            .setView(calendarView)
            .setPositiveButton("OK") { dialog, which ->
                // Get the selected date and update the dateTextView
                val selectedDateInMillis = calendarView.date
                val selectedDate = formatDate(selectedDateInMillis)
                dateTextView.text = selectedDate

                // Dismiss the dialog
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                // Dismiss the dialog without updating the dateTextView
                dialog.dismiss()
            }

        // Show the AlertDialog
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun formatDate(dateInMillis: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateInMillis
        return dateFormat.format(calendar.time)
    }

    private fun saveEvent() {
        val eventName = eventNameEditText.text.toString()
        val eventDetails = eventDetailsEditText.text.toString()
        val date = dateTextView.text.toString()
        val time = timeTextView.text.toString()

        // Implement your logic to save the event with the provided details
        // For example, you can use a database to store the event
        // You may also want to perform validation before saving

        // Show a message or perform any other actions as needed
    }
}
