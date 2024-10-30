package com.csce5430sec7proj.pethelper.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.csce5430sec7proj.pethelper.data.CategoryType


enum class VendorCategory(override val category: String) : CategoryType {
    VET("Vet"), PET_STORE("Pet Store"), GROOMER("Groomer"),
    TRAINER("Trainer"), BOARDING("Boarding"), DAYCARE("Daycare"),
    OTHER("Other")
}


@Entity(tableName = "vendors")
data class Vendor(
    // Inheriting properties from Person
    // This will take care of the ID field from Person
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "vendor_id")
    override val id: Int = 0,
    @ColumnInfo(name = "vendor_name")
    override val name: String,
    @ColumnInfo(name = "vendor_email")
    override val email: String?,
    @ColumnInfo(name = "vendor_phone")
    override val phone: String?,
    @ColumnInfo(name = "vendor_address")
    override val address: String?,

    // Vendor-specific properties
    @ColumnInfo(name = "vendor_category")
    val category: VendorCategory,
    @ColumnInfo(name = "vendor_description")
    val description: String?,
    @ColumnInfo(name = "vendor_secondary_phone")
    val secondaryPhone: String?,
) : Person(id, name, email, phone, address)

// Overriding the email column if needed
//@ColumnInfo(name = "vendor_email")
//override val email: String?,