package com.csce5430sec7proj.pethelper.data

import com.csce5430sec7proj.pethelper.data.daos.AppointmentDao
import com.csce5430sec7proj.pethelper.data.daos.PetDao
import com.csce5430sec7proj.pethelper.data.daos.RecordDao
import com.csce5430sec7proj.pethelper.data.daos.VaccinationDao
import com.csce5430sec7proj.pethelper.data.daos.VendorDao
import com.csce5430sec7proj.pethelper.data.entities.Appointment
import com.csce5430sec7proj.pethelper.data.entities.Pet
import com.csce5430sec7proj.pethelper.data.entities.Record
import com.csce5430sec7proj.pethelper.data.entities.Vaccination
import com.csce5430sec7proj.pethelper.data.entities.Vendor
import kotlinx.coroutines.flow.Flow


class PetHelperRepository(
    private val appointmentDao: AppointmentDao,
    private val petDao: PetDao,
    private val recordDao: RecordDao,
    private val vaccinationDao: VaccinationDao,
    private val vendorDao: VendorDao
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

    val getAppointmentsWithPetAndVendor = appointmentDao.getAppointmentsWithPetAndVendor()
    fun getAppointmentsWithPetAndVendorFilteredByPet(id: Int) = appointmentDao
        .getAppointmentsWithPetAndVendorFilteredByPetId(id)
    fun getAppointmentsWithPetAndVendorFilteredByVendor(id: Int) = appointmentDao
        .getAppointmentsWithPetAndVendorFilteredByVendorId(id)
    fun getAppointmentWithPetAndVendorFilteredByAppointment(id: Int) = appointmentDao
        .getAppointmentWithPetAndVendorFilteredById(id)


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

    val getRecordsWithPetAndVendor = recordDao.getRecordsWithPetAndVendor()
    fun getRecordsWithPetAndVendorFilteredByPet(id: Int) = recordDao
        .getRecordsWithPetAndVendorFilteredByPetId(id)
    fun getRecordsWithPetAndVendorFilteredByVendor(id: Int) = recordDao
        .getRecordsWithPetAndVendorFilteredByVendorId(id)
    fun getRecordWithPetAndVendorFilteredByRecord(id: Int) = recordDao
        .getRecordWithPetAndVendorFilteredById(id)

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

    // Vendors
    val getVendors = vendorDao.getAll()
    fun getVendor(id: Int) = vendorDao.getVendor(id)

    suspend fun insertVendor(vendor: Vendor) {
        vendorDao.insert(vendor)
    }
    suspend fun updateVendor(vendor: Vendor) {
        vendorDao.update(vendor)
    }
    suspend fun deleteVendor(vendor: Vendor) {
        vendorDao.delete(vendor)
    }
}
