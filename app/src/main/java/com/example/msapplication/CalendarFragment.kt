package com.example.msapplication

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText


class CalendarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Handle the date selection
            showEventInputForm(year, month, dayOfMonth)
        }

        return view
    }

    private fun showEventInputForm(year: Int, month: Int, dayOfMonth: Int) {
        val dialogView = layoutInflater.inflate(R.layout.event_input_form, null)
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Enter Event Details")

        val dialog = dialogBuilder.show()

        val eventName = dialogView.findViewById<EditText>(R.id.eventName!!)
        val eventDescription = dialogView.findViewById<EditText>(R.id.eventDescription!!)
        val saveEventButton = dialogView.findViewById<Button>(R.id.saveEventButton!!)

        saveEventButton.setOnClickListener {
            // Get the entered event details and save them
            val eventNameText = eventName.text.toString()
            val eventDescriptionText = eventDescription.text.toString()

            // Save the event details to your data storage (e.g., a database)
            val eventDatabaseHelper = EventDatabaseHelper(requireContext())
            val selectedDate = "$year-${month + 1}-$dayOfMonth"
            val event = Event(0, selectedDate, eventNameText, eventDescriptionText)
            eventDatabaseHelper.insertEvent(event)

            // Close the dialog
            dialog.dismiss()

        }
    }
}