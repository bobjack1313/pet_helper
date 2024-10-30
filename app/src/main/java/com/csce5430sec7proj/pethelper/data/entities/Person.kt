package com.csce5430sec7proj.pethelper.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Parent entity
@Entity(tableName = "persons")
open class Person(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "person_id")
    open val id: Int = 0,
    @ColumnInfo(name = "name")
    open val name: String,
    @ColumnInfo(name = "email")
    open val email: String?,
    @ColumnInfo(name = "phone")
    open val phone: String?,
    @ColumnInfo(name = "address")
    open val address: String?,
)