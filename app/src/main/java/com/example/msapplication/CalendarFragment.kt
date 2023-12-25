package com.example.msapplication

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarFragment : Fragment() {

    private lateinit var eventNameEditText: TextInputEditText
    private lateinit var eventDetailsEditText: TextInputEditText
    private lateinit var dateED: TextInputEditText
    private lateinit var timeEd: TextInputEditText
    private lateinit var saveEventButton: Button
    private lateinit var calendarView: CalendarView
    private lateinit var timePicker: TimePickerDialog
    private lateinit var dbHelper: EventDatabaseHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        eventNameEditText = view.findViewById(R.id.name)
        eventDetailsEditText = view.findViewById(R.id.details)
        dateED = view.findViewById(R.id.date)
        timeEd = view.findViewById(R.id.alarm)
        saveEventButton = view.findViewById(R.id.save_event_btn)


        // Set OnClickListener for dateTextView
        dateED.setOnClickListener {
            showDatePickerDialog()
        }

        saveEventButton.setOnClickListener {
            saveEvent()
        }

        dbHelper = EventDatabaseHelper(requireContext())

        // Set OnClickListener for time
        timeEd.setOnClickListener {
            showTimePickerDialog()
        }

        return view
    }


    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        // Initialize TimePickerDialog
        val timePicker = TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                // Update the timeEd with the selected time
                val formattedTime =
                    String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
                timeEd.text = Editable.Factory.getInstance().newEditable(formattedTime)
            },
            currentHour,
            currentMinute,
            true
        )

        // Show the TimePickerDialog
        timePicker.show()
    }

    private fun showDatePickerDialog() {
        // Initialize CalendarView
        val calendarView = CalendarView(requireContext())

        // Create an AlertDialog with CalendarView
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle("Select Date")
            .setView(calendarView)
            .setPositiveButton("OK") { dialog, which ->
                // Get the selected date and update the dateED
                val selectedDateInMillis = calendarView.date
                val selectedDate = formatDate(selectedDateInMillis)
                dateED.text = Editable.Factory.getInstance().newEditable(selectedDate)

                // Dismiss the dialog
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                // Dismiss the dialog without updating the dateED
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
        val date = dateED.text.toString()
        val time = timeEd.text.toString()

        // Validate the input (you can add more validation as needed)
        if (eventName.isEmpty() || date.isEmpty() || time.isEmpty()) {
            // Show an error message
            // For example, you can use a Toast
            showToast("Please fill in all the details")
            return
        }

        // Save the event to the database
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(EventContract.EventEntry.COLUMN_NAME, eventName)
            put(EventContract.EventEntry.COLUMN_DETAILS, eventDetails)
            put(EventContract.EventEntry.COLUMN_DATE, date)
            put(EventContract.EventEntry.COLUMN_TIME, time)
        }

        val newRowId = db?.insert(EventContract.EventEntry.TABLE_NAME, null, values)

        // Close the database
        db?.close()

        // Show a success message
        showToast("Event saved successfully")

        // Clear the input fields
        eventNameEditText.text = null
        eventDetailsEditText.text = null
        dateED.text = null
        timeEd.text = null
    }

    private fun showToast(message: String) {
        // Implement your logic to show a Toast message
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
