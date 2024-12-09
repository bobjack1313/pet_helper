package com.csce5430sec7proj.pethelper.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.csce5430sec7proj.pethelper.data.entities.Pet
import kotlinx.coroutines.flow.Flow


@Dao
interface PetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pet: Pet)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(pet: Pet)

    @Delete
    suspend fun delete(pet: Pet)

    @Query("SELECT * from pets WHERE pet_id = :id")
    fun getPet(id: Int): Flow<Pet>

    @Query("SELECT * from pets ORDER BY pet_name ASC")
    fun getAll(): Flow<List<Pet>>

    @Query("SELECT * FROM pets WHERE archived = 0 ORDER BY pet_name ASC")
    fun getAllPets(): Flow<List<Pet>>

    @Query("SELECT * FROM pets WHERE archived = 1 ORDER BY pet_name ASC")
    fun getArchivedPets(): Flow<List<Pet>>
}
