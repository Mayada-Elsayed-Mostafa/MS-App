package com.example.msapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.msapplication.model.Appointment

class AppointmentsAdapter(private var appointments: MutableList<Appointment> = mutableListOf()) :
    RecyclerView.Adapter<AppointmentsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val timeTimeTextView: TextView = itemView.findViewById(R.id.timeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_appointment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.titleTextView.text = appointment.title
        holder.dateTextView.text = "${appointment.date} at ${appointment.time}"
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    fun addAppointment(appointment: Appointment) {
        appointments.add(appointment)
        notifyItemInserted(appointments.size - 1)
    }
}
