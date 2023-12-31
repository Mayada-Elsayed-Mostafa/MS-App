package com.example.msapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.msapplication.AppointmentsAdapter
import com.example.msapplication.data.DailyTipsManager
import com.example.msapplication.OnEventSavedListener
import com.example.msapplication.R
import com.example.msapplication.data.domain.Appointment

class HomeFragment : Fragment(), OnEventSavedListener {

    private lateinit var appointmentsAdapter: AppointmentsAdapter
    private lateinit var dailyTipText: TextView
    private lateinit var appointmentsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Set up the welcome message
        val userName = view.findViewById<TextView>(R.id.welcomeMessageTV)
        userName.text = "Welcome Mayada"

        // Set up the Daily Tips section
        dailyTipText = view.findViewById(R.id.dailyTipText)
        updateDailyTip()

        // Initialize RecyclerView and set the adapter with an empty list
        appointmentsRecyclerView = view.findViewById<RecyclerView>(R.id.appointmentsRecyclerView)
        appointmentsAdapter = AppointmentsAdapter()
        appointmentsRecyclerView.adapter = appointmentsAdapter

        // Use requireContext() instead of this for obtaining the context
        appointmentsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    private fun updateDailyTip() {
        // Set the daily tip text using DailyTipsManager
        dailyTipText.text = DailyTipsManager.getDailyTip()
    }

    fun updateAppointmentsList(appointment: Appointment) {
        // Update the data set in your RecyclerView adapter
        // You should have a method in your RecyclerView adapter to add the new appointment to the list
        // For example, if you have a method named 'addAppointment' in your adapter, use it like this:
        appointmentsAdapter.addAppointment(appointment)
    }

    override fun onEventSaved(appointment: Appointment) {
        // Handle the saved event, e.g., update the RecyclerView
        updateAppointmentsList(appointment)
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}
