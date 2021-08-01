package com.example.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*

object StringUtils {

    private const val EEEDDMMMYYYY = "EEE, dd MMM yyyy"

    fun convertEEEDDMMMYYYY(date: Date): String {
        return getDateFormat(date, EEEDDMMMYYYY)
    }

    private fun getDateFormat(date: Date, format: String): String {
        val locale = Locale.getDefault()
        return SimpleDateFormat(format, locale).format(date)
    }
}