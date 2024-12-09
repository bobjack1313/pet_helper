package com.csce5430sec7proj.pethelper.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

// Define the PetType enum
enum class RecordType {
    MEDICAL, GROOMING, VACCINATION, TRAINING, DIET, OTHER
}

@Entity(tableName = "records")
data class Record(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "record_id")
    val id: Int = 0,
    @ColumnInfo(name = "pet_id_fk")
    val petIdFk: Int = 0,
    @ColumnInfo(name = "record_type")
    val type: RecordType,
    @ColumnInfo(name = "record_description")
    val description: String?="",
    @ColumnInfo(name = "record_date")
    val date: Date?=null,
    @ColumnInfo(name = "service_id_fk")
    val serviceIdFk: Int? = 0,
    @ColumnInfo(name = "record_cost")
    val cost: Double? = 0.0,
)
