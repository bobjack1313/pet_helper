package com.csce5430sec7proj.pethelper.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.csce5430sec7proj.pethelper.data.entities.Vendor
import kotlinx.coroutines.flow.Flow


@Dao
interface VendorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vendor: Vendor)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vendor: Vendor)

    @Delete
    suspend fun delete(vendor: Vendor)

    @Query("SELECT * from vendors WHERE vendor_id = :id")
    fun getVendor(id: Int): Flow<Vendor>

    @Query("SELECT * from vendors ORDER BY vendor_name ASC")
    fun getAll(): Flow<List<Vendor>>
}
