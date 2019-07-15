package com.zwh.android_byte_dance.beans

import java.util.*

class Note(id: Long) {

    var id = id
    var date: Date? = null
        get() = field
        set(value) {
            field = value
        }
    var state: State? = null
        get() = field
        set(value) {
            field = value
        }
    var content: String? = null
        get() = field
        set(value) {
            field = value
        }
    var priority: Priority? = null
        get() = field
        set(value) {
            field = value
        }

}