package com.zwh.android_byte_dance

class Data(title: String, no: Int, number: Int, hot_value: Tag) {
    enum class Tag { Hot, Suggestion, New, None }

    var title: String
    var no: Int
    var number: Int
    var hot_value: Tag

    init {
        this.title = title
        this.no = no
        this.number = number
        this.hot_value = hot_value
    }
}