package com.csce5430sec7proj.pethelper.data

import com.csce5430sec7proj.pethelper.data.entities.Pet
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Pet] from a given data source.
 */
interface PetsRepository {
    /**
     * Retrieve all the pets from the the given data source.
     */
    fun getAllPetsStream(): Flow<List<Pet>>

    /**
     * Retrieve an pet from the given data source that matches with the [id].
     */
    fun getPetsStream(id: Int): Flow<Pet?>

    /**
     * Insert pet in the data source
     */
    suspend fun insertPet(pet: Pet)

    /**
     * Delete pet from the data source
     */
    suspend fun deletePet(pet: Pet)

    /**
     * Update pet in the data source
     */
    suspend fun updatePet(pet: Pet)
}