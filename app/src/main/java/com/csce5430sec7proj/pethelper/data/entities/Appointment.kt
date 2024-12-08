package com.csce5430sec7proj.pethelper.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.csce5430sec7proj.pethelper.data.CategoryType
import java.util.Date


enum class AppointmentType(override val category: String) : CategoryType {
    VET("Vet"), GROOMER("Groomer"), TRAINER("Trainer"), BOARDING("Boarding"), DAYCARE("Daycare"), OTHER("Other")
}

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "appointment_id")
    val id: Int = 0,
    @ColumnInfo(name = "pet_id_fk")
    val petIdFk: Int = 0,
    @ColumnInfo(name = "appointment_description")
    val description: String,
    @ColumnInfo(name = "appointment_date")
    val date: Date,
    @ColumnInfo(name = "service_id_fk")
    val serviceIdFk: Int = 0,
    @ColumnInfo(name = "appointment_service_type")
    val serviceType: String
)
