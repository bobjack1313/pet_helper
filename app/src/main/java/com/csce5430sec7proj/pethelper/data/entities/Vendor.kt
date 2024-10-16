package com.csce5430sec7proj.pethelper.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vendors")
data class Vendor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "vendor_id")
    val id: Int = 0,
    @ColumnInfo(name = "vendor_name")
    val name: String,
    @ColumnInfo(name = "vendor_description")
    val description: String,
    @ColumnInfo(name = "vendor_phone")
    val phone: String,
    @ColumnInfo(name = "vendor_address")
    val address: String,
    @ColumnInfo(name = "vendor_email")
    val email: String,
    @ColumnInfo(name = "vendor_category")
    val category: String,
)
