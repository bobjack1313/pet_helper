package com.csce5430sec7proj.pethelper.data.daos

import androidx.room.*
import com.csce5430sec7proj.pethelper.data.entities.MedicalRecord

@Dao
interface MedicalRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: MedicalRecord)

    @Query("SELECT * FROM medical_records")
    suspend fun getAllRecords(): List<MedicalRecord>

    @Delete
    suspend fun delete(record: MedicalRecord)
}
