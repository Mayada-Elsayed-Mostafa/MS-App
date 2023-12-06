package com.example.msapplication

import android.content.Context
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

    companion object {
        const val DATABASE_NAME = "event.db"
        const val DATABASE_VERSION = 1
    }
}

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