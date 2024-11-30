package com.csce5430sec7proj.pethelper.data.daos


import com.csce5430sec7proj.pethelper.data.PetHelperDatabase
import com.csce5430sec7proj.pethelper.data.entities.Vendor
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ApplicationProvider
import com.csce5430sec7proj.pethelper.data.entities.VendorCategory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class VendorDaoTest {

    private lateinit var database: PetHelperDatabase
    private lateinit var vendorDao: VendorDao

    @Before
    fun setup() {
        // Initialize in-memory database
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PetHelperDatabase::class.java
        ).allowMainThreadQueries().build()

        vendorDao = database.vendorDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndRetrieveVendor() = runBlocking {
        val vendor1 = Vendor(
            id = 1001,
            name = "Happy Paws Clinic",
            email = "info@happypaws.com",
            phone = "555-1234",
            address = "123 Main St",
            category = VendorCategory.VET,
            description = "Veterinary clinic for small animals",
            secondaryPhone = "555-5678"
        )

        val vendor2 = Vendor(
            id = 1002,
            name = "Cozy Pet Spa",
            email = "contact@cozypetspa.com",
            phone = "555-8765",
            address = "456 Elm St",
            category = VendorCategory.GROOMER,
            description = "Luxury grooming services for pets",
            secondaryPhone = "555-4321"
        )

        // Insert vendors
        vendorDao.insert(vendor1)
        vendorDao.insert(vendor2)

        // Verify entries
        val vendors = vendorDao.getAll().first()
        assertEquals(2, vendors.size)

        // Gets by Name ASC
        val retrievedVendor1 = vendors[1]
        val retrievedVendor2 = vendors[0]

        assertEquals("Happy Paws Clinic", retrievedVendor1.name)
        assertEquals("Veterinary clinic for small animals", retrievedVendor1.description)
        assertEquals("555-1234", retrievedVendor1.phone)
        assertEquals(VendorCategory.VET, retrievedVendor1.category)

        assertEquals("Cozy Pet Spa", retrievedVendor2.name)
        assertEquals("Luxury grooming services for pets", retrievedVendor2.description)
        assertEquals("555-8765", retrievedVendor2.phone)
        assertEquals(VendorCategory.GROOMER, retrievedVendor2.category)
    }

    @Test
    fun updateVendor() = runBlocking {
        val vendor = Vendor(
            id = 1,
            name = "Happy Paws Clinic",
            email = "info@happypaws.com",
            phone = "555-1234",
            address = "123 Main St",
            category = VendorCategory.VET,
            description = "Veterinary clinic for small animals",
            secondaryPhone = "555-5678"
        )

        // Insert vendor
        vendorDao.insert(vendor)

        // Update vendor details
        val updatedVendor = vendor.copy(
            name = "Happy Paws Updated",
            phone = "555-9999",
            description = "Updated veterinary clinic description"
        )
        vendorDao.update(updatedVendor)

        // Verify update
        val retrievedVendor = vendorDao.getVendor(1).first()
        assertNotNull(retrievedVendor)
        assertEquals("Happy Paws Updated", retrievedVendor.name)
        assertEquals("555-9999", retrievedVendor.phone)
        assertEquals("Updated veterinary clinic description", retrievedVendor.description)
    }

    @Test
    fun deleteVendor() = runBlocking {
        val vendor = Vendor(
            id = 1001,
            name = "Happy Paws Clinic",
            email = "info@happypaws.com",
            phone = "555-1234",
            address = "123 Main St",
            category = VendorCategory.VET,
            description = "Veterinary clinic for small animals",
            secondaryPhone = "555-5678"
        )

        // Insert vendor
        vendorDao.insert(vendor)

        // Verify entry exists
        val vendors = vendorDao.getAll().first()
        assertEquals(1, vendors.size)

        // Delete vendor
        vendorDao.delete(vendors[0])

        // Verify deletion
        val updatedVendors = vendorDao.getAll().first()
        assertEquals(0, updatedVendors.size)

        val retrievedVendor = vendorDao.getVendor(1001).first()
        assertNull(retrievedVendor)
    }
}


