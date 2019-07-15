package com.zwh.android_byte_dance.beans

enum class State(intValue: Int) {
    TODO(0), DONE(1);

    val intValue = intValue

    companion object {
        fun from(intValue: Int): State {
            for (state in State.values()) {
                if (state.intValue == intValue) {
                    return state
                }
            }
            return TODO // default
        }
    }
}