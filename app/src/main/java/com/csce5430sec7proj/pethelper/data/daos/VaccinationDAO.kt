package com.csce5430sec7proj.pethelper.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.csce5430sec7proj.pethelper.data.entities.Vaccination
import kotlinx.coroutines.flow.Flow


@Dao
interface VaccinationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vaccination: Vaccination)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vaccination: Vaccination)

    @Delete
    suspend fun delete(vaccination: Vaccination)

    @Query("SELECT * from vaccinations WHERE vaccination_id = :id")
    fun getVaccination(id: Int): Flow<Vaccination>

    @Query("SELECT * from vaccinations ORDER BY vaccination_date DESC")
    fun getAllRecent(): Flow<List<Vaccination>>

    @Query("SELECT * from vaccinations ORDER BY vaccination_date ASC")
    fun getAllInOrder(): Flow<List<Vaccination>>
}