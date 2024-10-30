package com.csce5430sec7proj.pethelper.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date



@Entity(tableName = "pet_images")
data class PetImage(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pet_image_id")
    val id: Int,
    @ColumnInfo(name = "pet_id_fk")
    val petId: Int,
    val imageUri: String,
    val createdAt: Date?
)