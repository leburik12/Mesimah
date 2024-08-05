package com.example.mesimah.model

import androidx.room.TypeConverter
import java.util.Date

class Converter {
    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? {
         return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}