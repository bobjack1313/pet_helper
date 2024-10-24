package com.csce5430sec7proj.pethelper.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


// More info at
// https://developer.android.com/training/data-storage/room/defining-data

@Entity(tableName = "pets")
data class Pet(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pet_id")
    val id: Int,
    @ColumnInfo(name = "pet_name")
    val name: String,
    @ColumnInfo(name = "pet_breed")
    val breed: String,
    @ColumnInfo(name = "pet_color")
    val color: String,
    @ColumnInfo(name = "pet_age")
    val age: Int,
    @ColumnInfo(name = "pet_date_of_birth")
    val dateOfBirth: Date,

    // Not sure if we need this
    @ColumnInfo(name = "pet_aggression")
    val aggression: Double
)



/*
Pet Helper Object Models
Pet Object


VetAppointment Object
Attributes:
• appointmentId: String
• petId: String
• vetName: String
• date: Date
• reason: String
Relationships:
• many-to-1 with Pet
GroomingAppointment Object
Attributes:
• appointmentId: String
• petId: String
• groomingService: String
• date: Date
• serviceType: String
Relationships:
• many-to-1 with Pet
CareReminder Object (Health or Grooming)
Attributes:
• reminderId: String
• petId: String
• reminderType: String (e.g., feeding, exercise)
• date: Date
• repeatInterval: String (e.g., daily, weekly)
Relationships:
• many-to-1 with Pet
CareRecord Object (Health or Grooming)
Attributes:
• recordId: String
• petId: String
• date: Date
• description: String (vaccination, illness, etc.)
Relationships:
• many-to-1 with Pet
•
PetCareCost Object
Attributes:
• costId: String
• petId: String
• date: Date
• amount: Double
• description: String (food, grooming, vet, etc.)
Relationships:
• many-to-1 with Pet
PetService (Health or Grooming)
Attributes:
• serviceId: String
• name: String
• contactInfo: String (phone, email, etc.)
• serviceType: String (vet, groomer, boarding)
Relationships:
• 1-to-many with VetAppointment
• 1-to-many with GroomingAppointment
Object Relationship Details
Pet: Primary object. Connects to multiple VetAppointments, GroomingAppointments,
CareReminders, CareRecords, and PetCareCosts.
VetAppointment and GroomingAppointment: Each linked to a Pet and may also be associated
with a PetService.
CareReminder: Can contain repeated reminders for care routines and connects to the Pet object.
CareRecord: Tracks the pet’s health and care records, linked to Pet.
PetCareCost: Holds costs related to each pet, including vet, grooming, food, etc.
PetService: Stores contacts of services such as vets, groomers, and allows linking with
appointments.
 */