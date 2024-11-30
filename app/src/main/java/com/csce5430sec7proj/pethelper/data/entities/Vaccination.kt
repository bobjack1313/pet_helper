package com.csce5430sec7proj.pethelper.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


enum class VaccinationType {
    OTHER
}

@Entity(tableName = "vaccinations")
data class Vaccination(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "vaccination_id")
    val id: Int = 0,
    @ColumnInfo(name = "pet_id_fk")
    val petIdFk: Int = 0,
    @ColumnInfo(name = "vaccination_type")
    val type: VaccinationType,
    @ColumnInfo(name = "vaccination_name")
    val name: String?,
    @ColumnInfo(name = "vaccination_description")
    val description: String?,
    @ColumnInfo(name = "vaccination_date")
    val date: Date?,
    @ColumnInfo(name = "vaccination_expiration_date")
    val expirationDate: Date?,
)
