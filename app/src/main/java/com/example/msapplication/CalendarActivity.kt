package com.example.msapplication

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.msapplication.model.Appointment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarActivity : AppCompatActivity(), OnEventSavedListener {

    private lateinit var eventNameEditText: TextInputEditText
    private lateinit var eventDetailsEditText: TextInputEditText
    private lateinit var dateED: TextInputEditText
    private lateinit var timeEd: TextInputEditText
    private lateinit var saveEventButton: Button
    private lateinit var timePicker: TimePickerDialog
    private lateinit var dbHelper: EventDatabaseHelper
    private lateinit var onEventSavedListener: OnEventSavedListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        eventNameEditText = findViewById(R.id.name)
        eventDetailsEditText = findViewById(R.id.details)
        dateED = findViewById(R.id.date)
        timeEd = findViewById(R.id.alarm)
        saveEventButton = findViewById(R.id.save_event_btn)

        dateED.setOnClickListener {
            showDatePickerDialog()
        }

        onEventSavedListener = this // Set the activity itself as the listener
        saveEventButton.setOnClickListener {
            saveEvent()
        }

        dbHelper = EventDatabaseHelper(this)

        timeEd.setOnClickListener {
            showTimePickerDialog()
        }
    }


    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        // Initialize TimePickerDialog using the class property
        timePicker = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                // Update the timeEd with the selected time
                val formattedTime =
                    String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        hourOfDay,
                        minute
                    )
                timeEd.text =
                    Editable.Factory.getInstance().newEditable(formattedTime)
            },
            currentHour,
            currentMinute,
            true
        )

        // Show the TimePickerDialog
        timePicker.show()
    }


    private fun showDatePickerDialog() {
        // Inflate the custom layout for DatePicker
        val dialogView =
            LayoutInflater.from(this).inflate(R.layout.dialog_date_picker, null)
        val calendarView = dialogView.findViewById<CalendarView>(R.id.calendarView)

        // Initialize CalendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Get the selected date and update the dateED
            val selectedDateInMillis = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }.timeInMillis

            val selectedDate = formatDate(selectedDateInMillis)
            dateED.text = Editable.Factory.getInstance().newEditable(selectedDate)
        }

        // Create an AlertDialog with the custom layout
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Select Date")
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ ->
                // Dismiss the dialog
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
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

    override fun onEventSaved(appointment: Appointment) {
        // Notify the listener that an event has been saved
        onEventSavedListener.onEventSaved(appointment)

        // Update the Appointments RecyclerView in HomeFragment
        val homeFragment =
            supportFragmentManager.findFragmentByTag(HomeFragment.TAG) as HomeFragment?
        homeFragment?.updateAppointmentsList(appointment)
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

        // Use the newRowId as the 'id' for the new Appointment
        val savedAppointment = Appointment(newRowId ?: -1, eventName, date, time)

        // Notify the listener that an event has been saved
        onEventSavedListener.onEventSaved(savedAppointment)

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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}