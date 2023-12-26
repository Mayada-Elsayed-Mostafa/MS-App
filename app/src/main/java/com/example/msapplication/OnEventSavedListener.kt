package com.example.msapplication

import com.example.msapplication.model.Appointment

interface OnEventSavedListener {
    fun onEventSaved(appointment: Appointment)
}