package com.csce5430sec7proj.pethelper.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medical_records")
data class MedicalRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val date: String
)
