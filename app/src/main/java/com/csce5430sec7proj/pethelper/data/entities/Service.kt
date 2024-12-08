package com.csce5430sec7proj.pethelper.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.csce5430sec7proj.pethelper.data.CategoryType


enum class ServiceCategory(override val category: String) : CategoryType {
    VET("Vet"), PET_STORE("Pet Store"), GROOMER("Groomer"),
    TRAINER("Trainer"), BOARDING("Boarding"), DAYCARE("Daycare"),
    OTHER("Other")
}


@Entity(tableName = "services")
data class Service(
    // Inheriting properties from Person
    // This will take care of the ID field from Person
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "service_id")
    override val id: Int = 0,
    @ColumnInfo(name = "service_name")
    override val name: String,
    @ColumnInfo(name = "service_email")
    override val email: String?,
    @ColumnInfo(name = "service_phone")
    override val phone: String?,
    @ColumnInfo(name = "service_address")
    override val address: String?,

    // Service-specific properties
    @ColumnInfo(name = "service_category")
    val category: ServiceCategory,
    @ColumnInfo(name = "service_description")
    val description: String?,
    @ColumnInfo(name = "service_secondary_phone")
    val secondaryPhone: String?,
    @ColumnInfo(name = "service_notes")
    val notes: String?,
) : Person(id, name, email, phone, address)
