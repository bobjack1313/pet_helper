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
import com.csce5430sec7proj.pethelper.data.entities.Service
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
        ON A.pet_id_fk = P.pet_id INNER JOIN services AS V
        ON A.service_id_fk = V.service_id
    """)
    fun getAppointmentsWithPetAndService(): Flow<List<AppointmentsWithPetAndService>>

    @Query("""
        SELECT * FROM appointments AS A INNER JOIN pets AS P
        ON A.pet_id_fk = P.pet_id INNER JOIN services AS V
        ON A.service_id_fk = V.service_id WHERE A.pet_id_fk =:petId
    """)
    fun getAppointmentsWithPetAndServiceFilteredByPetId(petId: Int):
            Flow<List<AppointmentsWithPetAndService>>

    @Query("""
        SELECT * FROM appointments AS A INNER JOIN pets AS P
        ON A.pet_id_fk = P.pet_id INNER JOIN services AS V
        ON A.service_id_fk = V.service_id WHERE A.service_id_fk =:serviceId
    """)
    fun getAppointmentsWithPetAndServiceFilteredByServiceId(serviceId: Int):
            Flow<List<AppointmentsWithPetAndService>>

    @Query("""
        SELECT * FROM appointments AS A INNER JOIN pets AS P
        ON A.pet_id_fk = P.pet_id INNER JOIN services AS V
        ON A.service_id_fk = V.service_id WHERE A.appointment_id =:appointmentId
    """)
    fun getAppointmentWithPetAndServiceFilteredById(appointmentId: Int):
            Flow<AppointmentsWithPetAndService>
}

data class AppointmentsWithPetAndService(
    @Embedded val appointment: Appointment,
    @Embedded val pet: Pet,
    @Embedded val service: Service
)
