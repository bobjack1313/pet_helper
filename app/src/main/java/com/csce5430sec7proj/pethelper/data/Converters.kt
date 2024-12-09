package com.csce5430sec7proj.pethelper.data

import androidx.room.TypeConverter
import com.csce5430sec7proj.pethelper.data.entities.PetType
import java.util.Date

open class Converters {
    @TypeConverter
    fun toDate(date: Long?): Date? {
        return date?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    // New PetType converters
    @TypeConverter
    fun fromPetType(petType: PetType?): String? {
        return petType?.name
    }

    @TypeConverter
    fun toPetType(name: String?): PetType? {
        return name?.let { PetType.valueOf(it) }
    }

    // Convert a list of allergies to a comma-separated string
    @TypeConverter
    fun fromAllergiesList(allergies: List<String>?): String? {
        return allergies?.joinToString(",")
    }

    // Convert a comma-separated string back to a list of allergies
    @TypeConverter
    fun toAllergiesList(allergiesString: String?): List<String>? {
        return allergiesString?.split(",") ?: emptyList()
    }
}
