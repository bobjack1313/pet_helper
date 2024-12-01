package com.csce5430sec7proj.pethelper.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.csce5430sec7proj.pethelper.data.entities.VetContact

@Dao
interface VetContactDao {
    @Insert
    suspend fun insert(vetContact: VetContact)

    @Query("SELECT * FROM vet_contacts")
    suspend fun getAllContacts(): List<VetContact>
}
