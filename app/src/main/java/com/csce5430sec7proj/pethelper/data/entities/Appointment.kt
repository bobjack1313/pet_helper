package com.csce5430sec7proj.pethelper.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "appointments")
data class Appointment(
    @ColumnInfo(name = "appointment_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val petIdFk: Int = 0,
    val description: String,
    val date: Date,
    val vendorIdFk: Int = 0,
    val serviceType: String
)
