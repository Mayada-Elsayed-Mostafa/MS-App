package com.example.msapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private lateinit var appointmentsAdapter: AppointmentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Assuming you have a list of appointments
        val appointmentsList = listOf(
            Appointment("Meeting", "2023-11-15", "15:30"),
            Appointment("Doctor's Appointment", "2023-11-18", "10:00")
            // Add more appointments as needed
        )

        // Initialize RecyclerView and set the adapter
        val appointmentsRecyclerView = view.findViewById<RecyclerView>(R.id.appointmentsRecyclerView)
        appointmentsAdapter = AppointmentsAdapter(appointmentsList)
        appointmentsRecyclerView.adapter = appointmentsAdapter

        // Use requireContext() instead of this for obtaining the context
        appointmentsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }
}