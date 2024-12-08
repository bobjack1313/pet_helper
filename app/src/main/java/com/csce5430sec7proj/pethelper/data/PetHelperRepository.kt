package com.csce5430sec7proj.pethelper.data

import com.csce5430sec7proj.pethelper.data.daos.AppointmentDao
import com.csce5430sec7proj.pethelper.data.daos.PetDao
import com.csce5430sec7proj.pethelper.data.daos.RecordDao
import com.csce5430sec7proj.pethelper.data.daos.VaccinationDao
import com.csce5430sec7proj.pethelper.data.daos.ServiceDao
import com.csce5430sec7proj.pethelper.data.entities.Appointment
import com.csce5430sec7proj.pethelper.data.entities.Pet
import com.csce5430sec7proj.pethelper.data.entities.Record
import com.csce5430sec7proj.pethelper.data.entities.Vaccination
import com.csce5430sec7proj.pethelper.data.entities.Service
import kotlinx.coroutines.flow.Flow


class PetHelperRepository(
    private val appointmentDao: AppointmentDao,
    private val petDao: PetDao,
    private val recordDao: RecordDao,
    private val vaccinationDao: VaccinationDao,
    private val serviceDao: ServiceDao
) {
    // Pets
    val getAllPets = petDao.getAll()
    val getPets = petDao.getAllPets()
    fun getPet(id: Int) = petDao.getPet(id)

    suspend fun insertPet(pet: Pet) {
        petDao.insert(pet)
    }
    suspend fun updatePet(pet: Pet): Int {
        petDao.update(pet)
        return pet.id
    }
    suspend fun deletePet(pet: Pet) {
       petDao.delete(pet)
    }
    fun getArchivedPets(): Flow<List<Pet>> {
        return petDao.getArchivedPets()
    }
    // Appointments
    val getAppointments = appointmentDao.getAll()
    fun getAppointment(id: Int) = appointmentDao.getAppointment(id)

    suspend fun insertAppointment(appointment: Appointment) {
        appointmentDao.insert(appointment)
    }
    suspend fun updateAppointment(appointment: Appointment) {
        appointmentDao.update(appointment)
    }
    suspend fun deleteAppointment(appointment: Appointment) {
        appointmentDao.delete(appointment)
    }

    val getAppointmentsWithPetAndService = appointmentDao.getAppointmentsWithPetAndService()
    fun getAppointmentsWithPetAndServiceFilteredByPet(id: Int) = appointmentDao
        .getAppointmentsWithPetAndServiceFilteredByPetId(id)
    fun getAppointmentsWithPetAndServiceFilteredByService(id: Int) = appointmentDao
        .getAppointmentsWithPetAndServiceFilteredByServiceId(id)
    fun getAppointmentWithPetAndServiceFilteredByAppointment(id: Int) = appointmentDao
        .getAppointmentWithPetAndServiceFilteredById(id)


    // Records
    val getRecords = recordDao.getAll()
    fun getRecord(id: Int) = recordDao.getRecord(id)

    suspend fun insertRecord(record: Record) {
        recordDao.insert(record)
    }
    suspend fun updateRecord(record: Record) {
        recordDao.update(record)
    }
    suspend fun deleteRecord(record: Record) {
        recordDao.delete(record)
    }

    val getRecordsWithPetAndService = recordDao.getRecordsWithPetAndService()
    fun getRecordsWithPetAndServiceFilteredByPet(id: Int) = recordDao
        .getRecordsWithPetAndServiceFilteredByPetId(id)
    fun getRecordsWithPetAndServiceFilteredByService(id: Int) = recordDao
        .getRecordsWithPetAndServiceFilteredByServiceId(id)
    fun getRecordWithPetAndServiceFilteredByRecord(id: Int) = recordDao
        .getRecordWithPetAndServiceFilteredById(id)

    // Vaccinations
    val getVaccinesByRecent = vaccinationDao.getAllRecent()
    val getVaccinesByOrder = vaccinationDao.getAllInOrder()

    fun getVaccination(id: Int): Flow<Vaccination> {
        return vaccinationDao.getVaccination(id)
    }

    suspend fun insertVaccination(vaccination: Vaccination) {
        vaccinationDao.insert(vaccination)
    }
    suspend fun updateVaccination(vaccination: Vaccination) {
        vaccinationDao.update(vaccination)
    }
    suspend fun deleteVaccination(vaccination: Vaccination) {
        vaccinationDao.delete(vaccination)
    }

    // Services
    val getServices = serviceDao.getAll()
    fun getService(id: Int) = serviceDao.getService(id)

    suspend fun insertService(service: Service) {
        serviceDao.insert(service)
    }
    suspend fun updateService(service: Service) {
        serviceDao.update(service)
    }
    suspend fun deleteService(service: Service) {
        serviceDao.delete(service)
    }
}
