package com.csce5430sec7proj.pethelper.data.converters

import androidx.room.TypeConverter
import java.sql.Date

open class DateConverter {
    @TypeConverter
    fun toDate(date: Long?): Date? {
        return date?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}
