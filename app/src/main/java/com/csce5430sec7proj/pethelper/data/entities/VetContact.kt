package com.csce5430sec7proj.pethelper.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vet_contacts")
data class VetContact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val phoneNumber: String,
    val emailAddress: String,
    val message: String
)
