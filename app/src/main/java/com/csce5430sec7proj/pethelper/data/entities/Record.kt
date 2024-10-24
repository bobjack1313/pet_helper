package com.csce5430sec7proj.pethelper.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "records")
data class Record(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "record_id")
    val id: Int = 0,
    @ColumnInfo(name = "pet_id_fk")
    val petIdFk: Int = 0,
    @ColumnInfo(name = "record_description")
    val description: String,
    @ColumnInfo(name = "record_date")
    val date: Date,
    @ColumnInfo(name = "vendor_id_fk")
    val vendorIdFk: Int = 0,
    @ColumnInfo(name = "record_cost")
    val cost: Int = 0
)
