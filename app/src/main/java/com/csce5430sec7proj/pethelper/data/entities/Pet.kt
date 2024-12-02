package com.csce5430sec7proj.pethelper.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


// More info at
// https://developer.android.com/training/data-storage/room/defining-data

// Define the PetType enum
enum class PetType {
    DOG, CAT, FISH, BIRD, REPTILE, SNAKE, LIZARD, TURTLE, GERBIL, HAMSTER, PIG, CHICKEN, GOAT,
    SHEEP, BOAR, COW, HORSE, RABBIT, RAT, INSECT, SPIDER, WORM, OTHER
}

enum class PetGender {
    MALE, FEMALE, OTHER
}


@Entity(tableName = "pets")
data class Pet(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pet_id")
    val id: Int,
    @ColumnInfo(name = "pet_name")
    val name: String,
    @ColumnInfo(name = "pet_type")
    val type: PetType,
    @ColumnInfo(name = "pet_breed")
    val breed: String? = null,
    @ColumnInfo(name = "person_id_fk")
    val ownerId: Int? = null,
    val imageId: Int? = null,
    @ColumnInfo(name = "pet_color")
    val color: String? = null,
    @ColumnInfo(name = "pet_weight")
    val weight: Double? = null,
    // Date of birth
    @ColumnInfo(name = "pet_date_of_birth")
    val dateOfBirth: Date? = null,
    val imagePath: String? = null,
    // Stored as a String to handle up to 15 digits
    val microchipId: String? = null,
    // The registry or issuing company
    val microchipIssuer: String? = null,
    val aggression: Double? = 0.0,
    val gender: PetGender? = null,
    val sterilized: Boolean? = null,
    val allergies: List<String>? = null,
    val diet: String? = null,
    val training: String? = null,
    val titles: List<String>? = null,
    val notes: String? = null,
)
