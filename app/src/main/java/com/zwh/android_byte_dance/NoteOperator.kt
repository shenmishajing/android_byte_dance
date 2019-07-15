package com.zwh.android_byte_dance

import com.zwh.android_byte_dance.beans.Note

interface NoteOperator {

    abstract fun deleteNote(note: Note)

    abstract fun updateNote(note: Note)
}