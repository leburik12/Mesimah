package com.example.mesimah.model

import androidx.databinding.InverseMethod
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateConverters {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    @JvmStatic
    @InverseMethod("stringToDate")
    fun dateToString(dateMillis: Long) : String {
        return if (dateMillis != null) {
            dateFormat.format(Date(dateMillis))
        } else {
            ""
        }
    }

    @JvmStatic
    fun stringToDate(dateString: String): Long {
       return try {
           val date = dateFormat.parse(dateString)
           date?.time ?: 0L
       } catch (e: Exception) {
           0L
       }
    }
}