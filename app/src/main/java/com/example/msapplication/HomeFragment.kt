package com.example.msapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.msapplication.model.Appointment

class HomeFragment : Fragment() {

    private lateinit var appointmentsAdapter: AppointmentsAdapter
    private lateinit var dailyTipText: TextView

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

        // Assuming you have a list of appointments
        val appointmentsList = listOf(
            Appointment("Meeting", "2023-11-15", "15:30"),
            Appointment("Doctor's Appointment", "2023-11-18", "10:00")
            // Add more appointments as needed
        )

        // Initialize RecyclerView and set the adapter
        val appointmentsRecyclerView =
            view.findViewById<RecyclerView>(R.id.appointmentsRecyclerView)
        appointmentsAdapter = AppointmentsAdapter(appointmentsList)
        appointmentsRecyclerView.adapter = appointmentsAdapter

        // Use requireContext() instead of this for obtaining the context
        appointmentsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    private fun updateDailyTip() {
        // Set the daily tip text using DailyTipsManager
        dailyTipText.text = DailyTipsManager.getDailyTip()
    }
}
