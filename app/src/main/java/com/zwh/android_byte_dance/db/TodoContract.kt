package com.zwh.android_byte_dance.db

import android.provider.BaseColumns

class TodoContract {

    init {
    }

    companion object {
        val SQL_CREATE_NOTES = (
                "CREATE TABLE " + TodoNote.TABLE_NAME
                        + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + TodoNote.COLUMN_DATE + " INTEGER, "
                        + TodoNote.COLUMN_STATE + " INTEGER, "
                        + TodoNote.COLUMN_CONTENT + " TEXT, "
                        + TodoNote.COLUMN_PRIORITY + " INTEGER)")

        val SQL_ADD_PRIORITY_COLUMN =
            "ALTER TABLE " + TodoNote.TABLE_NAME + " ADD " + TodoNote.COLUMN_PRIORITY + " INTEGER"
    }

    class TodoNote : BaseColumns {
        companion object {
            val TABLE_NAME = "note"

            val COLUMN_DATE = "date"
            val COLUMN_STATE = "state"
            val COLUMN_CONTENT = "content"
            val COLUMN_PRIORITY = "priority"
        }
    }
}