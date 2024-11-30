package com.csce5430sec7proj.pethelper.data.daos

import com.csce5430sec7proj.pethelper.data.PetHelperDatabase
import com.csce5430sec7proj.pethelper.data.entities.Vaccination
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ApplicationProvider
import com.csce5430sec7proj.pethelper.data.entities.VaccinationType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date


@RunWith(AndroidJUnit4::class)
class VaccinationDaoTest {

    private lateinit var database: PetHelperDatabase
    private lateinit var vaccinationDao: VaccinationDao

    @Before
    fun setup() {
        // Initialize in-memory database
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PetHelperDatabase::class.java
        ).allowMainThreadQueries().build()

        vaccinationDao = database.vaccinationDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndRetrieveVaccination() = runBlocking {
        val vaccination1 = Vaccination(
            id = 1001,
            petIdFk = 1,
            name = "Rabies Vaccine",
            type = VaccinationType.OTHER,
            date = Date(System.currentTimeMillis() - 365 * 24 * 60 * 60 * 1000L),
            expirationDate = Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000L),
            description = "Prevents rabies in dogs."
        )

        val vaccination2 = Vaccination(
            id = 1002,
            petIdFk = 1,
            name = "Distemper Vaccine",
            type = VaccinationType.OTHER,
            date = Date(System.currentTimeMillis() - 180 * 24 * 60 * 60 * 1000L),
            expirationDate = Date(System.currentTimeMillis() + 730 * 24 * 60 * 60 * 1000L),
            description = "Protects against distemper."
        )

        // Insert vaccinations
        vaccinationDao.insert(vaccination1)
        vaccinationDao.insert(vaccination2)

        // Retrieve all vaccinations
        val vaccinations = vaccinationDao.getAllInOrder().first()
        assertEquals(2, vaccinations.size)

        val retrievedVaccination1 = vaccinations[0]
        val retrievedVaccination2 = vaccinations[1]

        assertEquals("Rabies Vaccine", retrievedVaccination1.name)
        assertEquals("Prevents rabies in dogs.", retrievedVaccination1.description)

        assertEquals("Distemper Vaccine", retrievedVaccination2.name)
        assertEquals("Protects against distemper.", retrievedVaccination2.description)

        val vaccinations2 = vaccinationDao.getAllRecent().first()
        val retrievedVaccination3 = vaccinations2[1]
        val retrievedVaccination4 = vaccinations2[0]

        assertEquals("Rabies Vaccine", retrievedVaccination3.name)
        assertEquals("Prevents rabies in dogs.", retrievedVaccination3.description)

        assertEquals("Distemper Vaccine", retrievedVaccination4.name)
        assertEquals("Protects against distemper.", retrievedVaccination4.description)
    }

    @Test
    fun updateVaccination() = runBlocking {
        val vaccination = Vaccination(
            id = 1001,
            petIdFk = 1,
            name = "Rabies Vaccine",
            type = VaccinationType.OTHER,
            date = Date(),
            expirationDate = Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000L),
            description = "Prevents rabies in dogs."
        )

        // Insert vaccination
        vaccinationDao.insert(vaccination)

        // Update vaccination details
        val updatedVaccination = vaccination.copy(
            name = "Updated Rabies Vaccine",
            expirationDate = Date(System.currentTimeMillis() + 730 * 24 * 60 * 60 * 1000L)
        )
        vaccinationDao.update(updatedVaccination)

        // Retrieve and verify update
        val retrievedVaccination = vaccinationDao.getVaccination(updatedVaccination.id).first()
        assertNotNull(retrievedVaccination)
        assertEquals("Updated Rabies Vaccine", retrievedVaccination.name)
        assertEquals(updatedVaccination.expirationDate, retrievedVaccination.expirationDate)
    }

    @Test
    fun deleteVaccination() = runBlocking {
        val vaccination = Vaccination(
            id = 1001,
            petIdFk = 1,
            name = "Rabies Vaccine",
            type = VaccinationType.OTHER,
            date = Date(),
            expirationDate = Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000L),
            description = "Prevents rabies in dogs."
        )

        // Insert vaccination
        vaccinationDao.insert(vaccination)

        // Verify it exists
        val vaccinations = vaccinationDao.getAllInOrder().first()
        assertEquals(1, vaccinations.size)

        // Delete the vaccination
        vaccinationDao.delete(vaccinations[0])

        // Verify deletion
        val updatedVaccinations = vaccinationDao.getAllInOrder().first()
        assertEquals(0, updatedVaccinations.size)

        val retrievedVaccination = vaccinationDao.getVaccination(vaccination.id).first()
        assertNull(retrievedVaccination)
    }
}
