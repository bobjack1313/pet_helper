package com.csce5430sec7proj.pethelper.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.csce5430sec7proj.pethelper.data.entities.Pet
import com.csce5430sec7proj.pethelper.data.entities.PetImage
import com.csce5430sec7proj.pethelper.data.entities.Record
import com.csce5430sec7proj.pethelper.data.entities.Vendor
import kotlinx.coroutines.flow.Flow

@Dao
interface PetImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(petImage: PetImage)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(petImage: PetImage)

    @Delete
    suspend fun delete(petImage: PetImage)

    // Adjusted return type for getPetImages
    @Query("SELECT * from pet_images WHERE pet_image_id = :id")
    fun getPetImageById(id: Int): Flow<PetImage>

    // This function returns a list of images along with their corresponding pets
    @Query("""
        SELECT * FROM pet_images AS PI 
        INNER JOIN pets AS P ON PI.pet_id_fk = P.pet_id
    """)
    fun getImagesForPet(): Flow<List<ImagesOfPet>>

    @Query("SELECT * from pets ORDER BY pet_name ASC")
    fun getAllPets(): Flow<List<Pet>>
}

// Data class to hold images of pets with their associated pet entity
data class ImagesOfPet(
    @Embedded val petImage: PetImage,
    @Embedded val pet: Pet
)
