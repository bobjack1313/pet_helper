package com.csce5430sec7proj.pethelper.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.csce5430sec7proj.pethelper.data.entities.Service
import kotlinx.coroutines.flow.Flow


@Dao
interface ServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(service: Service)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(service: Service)

    @Delete
    suspend fun delete(service: Service)

    @Query("SELECT * from services WHERE service_id = :id")
    fun getService(id: Int): Flow<Service>

    @Query("SELECT * from services ORDER BY service_name ASC")
    fun getAll(): Flow<List<Service>>
}
