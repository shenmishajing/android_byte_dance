package com.zwh.android_byte_dance

import java.util.ArrayList
import kotlin.random.Random

class DataFactory {

    //    fun getSingleData(info: String): Data {
//        return Data(info)
//    }
//
//    fun getData(): List<Data> {
//        return getData(DEFAULT_SIZE)
//    }
    companion object {
        val DEFAULT_SIZE = 10

        val NAMES =
            arrayOf(
                "Beijing",
                "Tokyo",
                "Paris",
                "London",
                "Reykjavik",
                "Jerusalem",
                "Doha",
                "Brasilia",
                "Havana",
                "Ottawa"
            )

        fun getData(size: Int): List<Data> {
            var size = size
            if (size > DEFAULT_SIZE) {
                size = DEFAULT_SIZE
            }
            val list = ArrayList<Data>()
            for (i in 0 until size) {
                val data = Data(NAMES[i], i, Random.nextInt(), Data.Tag.None)
                list.add(data)
            }
            return list
        }
    }
}