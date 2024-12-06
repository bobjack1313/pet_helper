package com.csce5430sec7proj.pethelper.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.csce5430sec7proj.pethelper.data.entities.VetContact
import kotlinx.coroutines.flow.Flow

@Dao
interface VetContactDao {

    // 插入新的联系信息
    @Insert
    suspend fun insert(vetContact: VetContact): Long

    // 获取所有联系信息，使用 Flow 来实现实时数据流
    @Query("SELECT * FROM vet_contacts")
    fun getAllContacts(): Flow<List<VetContact>>

    // 更新已有的联系信息
    @Update
    suspend fun update(vetContact: VetContact)

    // 删除指定的联系信息
    @Delete
    suspend fun delete(vetContact: VetContact)

    // 根据电话号码查找联系信息（示例）
    @Query("SELECT * FROM vet_contacts WHERE phoneNumber LIKE :phoneNumber")
    fun getContactsByPhoneNumber(phoneNumber: String): Flow<List<VetContact>>
}
