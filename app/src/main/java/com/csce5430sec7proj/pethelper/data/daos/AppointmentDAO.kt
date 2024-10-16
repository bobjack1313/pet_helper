package com.csce5430sec7proj.pethelper.data.daos

import com.csce5430sec7proj.pethelper.data.entities.Appointment

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.csce5430sec7proj.pethelper.data.entities.Pet
import com.csce5430sec7proj.pethelper.data.entities.Vendor
import kotlinx.coroutines.flow.Flow


@Dao
interface AppointmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appointment: Appointment)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(appointment: Appointment)

    @Delete
    suspend fun delete(appointment: Appointment)

    @Query("SELECT * from appointments WHERE appointment_id = :id")
    fun getAppointment(id: Int): Flow<Appointment>

    @Query("SELECT * from appointments")
    fun getAll(): Flow<List<Appointment>>

    @Query("""
        SELECT * FROM appointments AS A INNER JOIN pets AS P
        ON A.pet_id_fk = P.pet_id INNER JOIN vendors AS V
        ON A.vendor_id_fk = V.vendor_id
    """)
    fun getAppointmentsWithPetAndVendor(): Flow<List<AppointmentsWithPetAndVendor>>

    @Query("""
        SELECT * FROM appointments AS A INNER JOIN pets AS P
        ON A.pet_id_fk = P.pet_id INNER JOIN vendors AS V
        ON A.vendor_id_fk = V.vendor_id WHERE A.pet_id_fk =:petId
    """)
    fun getAppointmentsWithPetAndVendorFilteredByPetId(petId: Int):
            Flow<List<AppointmentsWithPetAndVendor>>

    @Query("""
        SELECT * FROM appointments AS A INNER JOIN pets AS P
        ON A.pet_id_fk = P.pet_id INNER JOIN vendors AS V
        ON A.vendor_id_fk = V.vendor_id WHERE A.vendor_id_fk =:vendorId
    """)
    fun getAppointmentsWithPetAndVendorFilteredByVendorId(vendorId: Int):
            Flow<List<AppointmentsWithPetAndVendor>>

    @Query("""
        SELECT * FROM appointments AS A INNER JOIN pets AS P
        ON A.pet_id_fk = P.pet_id INNER JOIN vendors AS V
        ON A.vendor_id_fk = V.vendor_id WHERE A.appointment_id =:appointmentId
    """)
    fun getAppointmentWithPetAndVendorFilteredById(appointmentId: Int):
            Flow<AppointmentsWithPetAndVendor>
}

data class AppointmentsWithPetAndVendor(
    @Embedded val appointment: Appointment,
    @Embedded val pet: Pet,
    @Embedded val vendor: Vendor
)

/*
Relationships:
• many-to-1 with Pet
• many-to-1 with Vendor
*/
