package com.woosung.domain.model

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@JvmInline
value class DateTime(private val date: Date) {

    fun time(): Long {
        return date.time
    }

    fun toDateString(): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date.time
        val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREAN)
        return formatter.format(calendar.time).toString()
    }

    companion object {
        fun fromTime(dateTime: String): DateTime {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ", Locale.KOREAN)
            return DateTime(formatter.parse(dateTime))
        }
    }
}