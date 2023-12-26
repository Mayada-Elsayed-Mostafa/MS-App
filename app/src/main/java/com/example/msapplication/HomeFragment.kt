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

class HomeFragment : Fragment(), CalendarActivity.OnEventSavedListener {

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

        // Initialize RecyclerView and set the adapter with an empty list
        val appointmentsRecyclerView =
            view.findViewById<RecyclerView>(R.id.appointmentsRecyclerView)
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

    override fun onEventSaved(appointment: Appointment) {
        // Handle the saved event, e.g., update the RecyclerView
        appointmentsAdapter.addAppointment(appointment)
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}
