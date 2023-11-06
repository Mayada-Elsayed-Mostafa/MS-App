package com.example.msapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class EventDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "events.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_EVENTS = "events"

        private const val COLUMN_ID = "id"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_EVENTS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_DATE TEXT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_DESCRIPTION TEXT" +
                ");"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Implement database upgrade logic if needed
    }

    fun insertEvent(event: Event) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_DATE, event.date)
        values.put(COLUMN_NAME, event.name)
        values.put(COLUMN_DESCRIPTION, event.description)
        val newRowId = db.insert(TABLE_EVENTS, null, values)
        db.close()
        Log.d("EventDatabaseHelper", "Inserted event with ID: $newRowId")
    }

    fun getEventsForDate(date: String): List<Event> {
        val events = mutableListOf<Event>()
        val db = readableDatabase

        val query = "SELECT * FROM $TABLE_EVENTS WHERE $COLUMN_DATE = ?"
        Log.d("DatabaseQuery", "Query: $query, Date: $date")
        val cursor = db.rawQuery(query, arrayOf(date))

        if (cursor.moveToFirst()) {
            do {
                // Log event details for debugging
                val idIndex = cursor.getColumnIndex(COLUMN_ID)
                val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
                val descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION)

                if (idIndex != -1 && nameIndex != -1 && descriptionIndex != -1) {
                    val id = cursor.getLong(idIndex)
                    val name = cursor.getString(nameIndex)
                    val description = cursor.getString(descriptionIndex)

                    val event = Event(id, date, name, description)
                    events.add(event)
                }
            } while (cursor.moveToNext())
        } else {
            Log.d("DatabaseQuery", "No events found for date: $date")
        }

        cursor.close()
        db.close()
        Log.d("EventDatabaseHelper", "Retrieved ${events.size} events for date: $date")

        return events
    }
}
