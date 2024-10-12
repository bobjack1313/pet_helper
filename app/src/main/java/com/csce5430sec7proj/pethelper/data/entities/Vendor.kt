package com.csce5430sec7proj.pethelper.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vendors")
data class Vendor(
    @ColumnInfo(name = "vendor_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val phone: String,
    val address: String,
    val email: String,
    val category: String,
)
