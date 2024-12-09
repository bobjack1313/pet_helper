package com.csce5430sec7proj.pethelper.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.csce5430sec7proj.pethelper.data.entities.Pet
import com.csce5430sec7proj.pethelper.data.entities.Record
import com.csce5430sec7proj.pethelper.data.entities.Service
import kotlinx.coroutines.flow.Flow


@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: Record)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(record: Record)

    @Delete
    suspend fun delete(record: Record)

    @Query("SELECT * from records WHERE record_id = :id")
    fun getRecord(id: Int): Flow<Record>

    @Query("SELECT * from records ORDER BY record_date ASC")
    fun getAll(): Flow<List<Record>>

    @Query("""
        SELECT * FROM records AS R INNER JOIN pets AS P
        ON R.pet_id_fk = P.pet_id INNER JOIN services AS V
        ON R.service_id_fk = V.service_id
    """)
    fun getRecordsWithPetAndService(): Flow<List<RecordsWithPetAndService>>

    @Query("""
        SELECT * FROM records AS R INNER JOIN pets AS P
        ON R.pet_id_fk = P.pet_id INNER JOIN services AS V
        ON R.service_id_fk = V.service_id WHERE R.pet_id_fk =:petId
    """)
    fun getRecordsWithPetAndServiceFilteredByPetId(petId: Int):
            Flow<List<RecordsWithPetAndService>>

    @Query("""
        SELECT * FROM records AS R INNER JOIN pets AS P
        ON R.pet_id_fk = P.pet_id INNER JOIN services AS V
        ON R.service_id_fk = V.service_id WHERE R.service_id_fk =:serviceId
    """)
    fun getRecordsWithPetAndServiceFilteredByServiceId(serviceId: Int):
            Flow<List<RecordsWithPetAndService>>

    @Query("""
        SELECT * FROM records AS R INNER JOIN pets AS P
        ON R.pet_id_fk = P.pet_id INNER JOIN services AS V
        ON R.service_id_fk = V.service_id WHERE R.record_id =:recordId
    """)
    fun getRecordWithPetAndServiceFilteredById(recordId: Int):
            Flow<RecordsWithPetAndService>
}

data class RecordsWithPetAndService(
    @Embedded val record: Record,
    @Embedded val pet: Pet,
    @Embedded val service: Service
)
