package com.csce5430sec7proj.pethelper.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.csce5430sec7proj.pethelper.data.entities.Pet
import kotlinx.coroutines.flow.Flow

// More info at
// https://developer.android.com/training/data-storage/room/accessing-data

@Dao
interface PetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pet: Pet)

    @Update
    suspend fun update(pet: Pet)

    @Delete
    suspend fun delete(pet: Pet)

    @Query("SELECT * from pets WHERE id = :id")
    fun getPet(id: Int): Flow<Pet>

    @Query("SELECT * from pets ORDER BY name ASC")
    fun getAll(): Flow<List<Pet>>
}


/*
Relationships:
• 1-to-many with VetAppointment
• 1-to-many with GroomingAppointment
• 1-to-many with CareReminder
• 1-to-many with CareRecord
• 1-to-many with PetCareCost
 */