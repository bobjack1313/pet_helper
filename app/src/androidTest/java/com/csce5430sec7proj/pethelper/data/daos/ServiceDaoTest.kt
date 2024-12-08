package com.csce5430sec7proj.pethelper.data.daos


import com.csce5430sec7proj.pethelper.data.PetHelperDatabase
import com.csce5430sec7proj.pethelper.data.entities.Service
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ApplicationProvider
import com.csce5430sec7proj.pethelper.data.entities.ServiceCategory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ServiceDaoTest {

    private lateinit var database: PetHelperDatabase
    private lateinit var serviceDao: ServiceDao

    @Before
    fun setup() {
        // Initialize in-memory database
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PetHelperDatabase::class.java
        ).allowMainThreadQueries().build()

        serviceDao = database.serviceDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndRetrieveService() = runBlocking {
        val service1 = Service(
            id = 1001,
            name = "Happy Paws Clinic",
            email = "info@happypaws.com",
            phone = "555-1234",
            address = "123 Main St",
            category = ServiceCategory.VET,
            description = "Veterinary clinic for small animals",
            secondaryPhone = "555-5678",
            notes = "Notes here"
        )

        val service2 = Service(
            id = 1002,
            name = "Cozy Pet Spa",
            email = "contact@cozypetspa.com",
            phone = "555-8765",
            address = "456 Elm St",
            category = ServiceCategory.GROOMER,
            description = "Luxury grooming services for pets",
            secondaryPhone = "555-4321",
            notes = "Notes here"
        )

        // Insert services
        serviceDao.insert(service1)
        serviceDao.insert(service2)

        // Verify entries
        val services = serviceDao.getAll().first()
        assertEquals(2, services.size)

        // Gets by Name ASC
        val retrievedService1 = services[1]
        val retrievedService2 = services[0]

        assertEquals("Happy Paws Clinic", retrievedService1.name)
        assertEquals("Veterinary clinic for small animals", retrievedService1.description)
        assertEquals("555-1234", retrievedService1.phone)
        assertEquals(ServiceCategory.VET, retrievedService1.category)

        assertEquals("Cozy Pet Spa", retrievedService2.name)
        assertEquals("Luxury grooming services for pets", retrievedService2.description)
        assertEquals("555-8765", retrievedService2.phone)
        assertEquals(ServiceCategory.GROOMER, retrievedService2.category)
    }

    @Test
    fun updateService() = runBlocking {
        val service = Service(
            id = 1,
            name = "Happy Paws Clinic",
            email = "info@happypaws.com",
            phone = "555-1234",
            address = "123 Main St",
            category = ServiceCategory.VET,
            description = "Veterinary clinic for small animals",
            secondaryPhone = "555-5678",
            notes = "Notes here"
        )

        // Insert service
        serviceDao.insert(service)

        // Update service details
        val updatedService = service.copy(
            name = "Happy Paws Updated",
            phone = "555-9999",
            description = "Updated veterinary clinic description"
        )
        serviceDao.update(updatedService)

        // Verify update
        val retrievedService = serviceDao.getService(1).first()
        assertNotNull(retrievedService)
        assertEquals("Happy Paws Updated", retrievedService.name)
        assertEquals("555-9999", retrievedService.phone)
        assertEquals("Updated veterinary clinic description", retrievedService.description)
    }

    @Test
    fun deleteService() = runBlocking {
        val service = Service(
            id = 1001,
            name = "Happy Paws Clinic",
            email = "info@happypaws.com",
            phone = "555-1234",
            address = "123 Main St",
            category = ServiceCategory.VET,
            description = "Veterinary clinic for small animals",
            secondaryPhone = "555-5678",
            notes = "Notes here"
        )

        // Insert service
        serviceDao.insert(service)

        // Verify entry exists
        val services = serviceDao.getAll().first()
        assertEquals(1, services.size)

        // Delete service
        serviceDao.delete(services[0])

        // Verify deletion
        val updatedServices = serviceDao.getAll().first()
        assertEquals(0, updatedServices.size)

        val retrievedService = serviceDao.getService(1001).first()
        assertNull(retrievedService)
    }
}


