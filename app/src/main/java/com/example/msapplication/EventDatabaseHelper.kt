package com.example.msapplication

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class EventDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(EventContract.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(EventContract.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    // Add this method to retrieve events for a specific date
    fun getEventsForDate(date: String): List<Event> {
        val events = mutableListOf<Event>()
        val db = readableDatabase

        // Define the columns you want to retrieve
        val projection = arrayOf(
            EventContract.EventEntry.COLUMN_NAME,
            EventContract.EventEntry.COLUMN_DETAILS,
            EventContract.EventEntry.COLUMN_DATE,
            EventContract.EventEntry.COLUMN_TIME
        )

        // Define the selection and selectionArgs to filter by date
        val selection = "${EventContract.EventEntry.COLUMN_DATE} = ?"
        val selectionArgs = arrayOf(date)

        // Perform the query
        val cursor: Cursor = db.query(
            EventContract.EventEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        // Iterate through the cursor and add events to the list
        with(cursor) {
            while (moveToNext()) {
                val eventName =
                    getString(getColumnIndexOrThrow(EventContract.EventEntry.COLUMN_NAME))
                val eventDetails =
                    getString(getColumnIndexOrThrow(EventContract.EventEntry.COLUMN_DETAILS))
                val eventDate =
                    getString(getColumnIndexOrThrow(EventContract.EventEntry.COLUMN_DATE))
                val eventTime =
                    getString(getColumnIndexOrThrow(EventContract.EventEntry.COLUMN_TIME))

                // Create an Event object and add it to the list
                val event = Event(eventName, eventDetails, eventDate, eventTime)
                events.add(event)
            }
        }

        // Close the cursor and database
        cursor.close()
        db.close()

        return events
    }

    companion object {
        const val DATABASE_NAME = "event.db"
        const val DATABASE_VERSION = 1
    }
}

data class Event(
    val name: String,
    val details: String,
    val date: String,
    val time: String
)

object EventContract {
    object EventEntry : BaseColumns {
        const val TABLE_NAME = "event"
        const val COLUMN_NAME = "name"
        const val COLUMN_DETAILS = "details"
        const val COLUMN_DATE = "date"
        const val COLUMN_TIME = "time"
    }

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${EventEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${EventEntry.COLUMN_NAME} TEXT," +
                "${EventEntry.COLUMN_DETAILS} TEXT," +
                "${EventEntry.COLUMN_DATE} TEXT," +
                "${EventEntry.COLUMN_TIME} TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${EventEntry.TABLE_NAME}"
}
