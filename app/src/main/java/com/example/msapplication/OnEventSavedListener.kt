package com.example.msapplication

import com.example.msapplication.data.domain.Appointment

interface OnEventSavedListener {
    fun onEventSaved(appointment: Appointment)
}