package com.example.msapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import kotlin.random.Random


class AppointmentsAdapter(private val appointments: List<Appointment>) :
    RecyclerView.Adapter<AppointmentsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.appointmentTitleTextView)
        val dateTimeTextView: TextView = itemView.findViewById(R.id.appointmentDateTimeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_appointment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.titleTextView.text = appointment.title
        holder.dateTimeTextView.text = "${appointment.date} at ${appointment.time}"

        // Schedule a notification for each appointment
        scheduleAppointmentNotification(holder.itemView.context, appointment)
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    private fun scheduleAppointmentNotification(context: Context, appointment: Appointment) {
        val notificationManager = NotificationManagerCompat.from(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "appointment_channel_id",
                "Appointment Reminders",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Check if the app has the necessary permission to show notifications
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.VIBRATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(context, HomeActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val notification = NotificationCompat.Builder(context, "appointment_channel_id")
                .setContentTitle("Appointment Reminder")
                .setContentText("${appointment.title} at ${appointment.time}")
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            val notificationId = Random.nextInt()
            notificationManager.notify(notificationId, notification)
        } else {
            // Handle the case where the app doesn't have the required permission
            // You can request the permission here or take appropriate action
            // For simplicity, let's just log a message for now
            Log.e("Permission Error", "Permission to show notifications is not granted.")
        }
    }

}
