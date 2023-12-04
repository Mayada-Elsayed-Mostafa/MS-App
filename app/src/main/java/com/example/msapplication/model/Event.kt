package com.example.msapplication.model

data class Event(
    val id: Long,   // Unique identifier for the event
    val date: String,
    val name: String,
    val description: String
)
