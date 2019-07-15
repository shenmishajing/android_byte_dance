package com.zwh.android_byte_dance.beans

import android.graphics.Color

enum class Priority(intValue: Int, color: Int) {
    High(2, Color.RED),
    Medium(1, Color.GREEN),
    Low(0, Color.WHITE);

    var intValue = intValue
    var color = color

    companion object {
        fun from(intValue: Int): Priority {
            for (priority in Priority.values()) {
                if (priority.intValue == intValue) {
                    return priority
                }
            }
            return Priority.Low // default
        }
    }
}