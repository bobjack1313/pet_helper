package com.csce5430sec7proj.pethelper.data.daos

import com.csce5430sec7proj.pethelper.data.PetHelperDatabase
import com.csce5430sec7proj.pethelper.data.entities.Appointment
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date


@RunWith(AndroidJUnit4::class)
class AppointmentDaoTest {

    private lateinit var database: PetHelperDatabase
    private lateinit var appointmentDao: AppointmentDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PetHelperDatabase::class.java
        ).allowMainThreadQueries().build()

        appointmentDao = database.appointmentDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertAndRetrieve() = runBlocking {
        val appointment = Appointment(
            id = 1,
            petIdFk = 1,
            description = "Vet Visit",
            date = Date(),
            vendorIdFk = 1,
            serviceType = "Vet"
        )
        appointmentDao.insert(appointment)

        val appointments = appointmentDao.getAll().first()
        println("Appointments: $appointments") // Debugging

        // Assertions
        assertEquals(1, appointments.size)
        assertEquals("Vet Visit", appointments[0].description)
        assertEquals(1, appointments[0].petIdFk)
        assertEquals(1, appointments[0].vendorIdFk)
        println("Appointments: $appointments") // Debugging
    }
}