package com.csce5430sec7proj.pethelper.data.daos

import com.csce5430sec7proj.pethelper.data.PetHelperDatabase
import com.csce5430sec7proj.pethelper.data.entities.Pet
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ApplicationProvider
import com.csce5430sec7proj.pethelper.data.entities.PetGender
import com.csce5430sec7proj.pethelper.data.entities.PetType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date


@RunWith(AndroidJUnit4::class)
class PetDaoTest {

    private lateinit var database: PetHelperDatabase
    private lateinit var petDao: PetDao

    @Before
    fun setup() {
        // Initialize in-memory database
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PetHelperDatabase::class.java
        ).allowMainThreadQueries().build()

        petDao = database.petDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndRetrievePet() = runBlocking {

        val pet1 = Pet(1001, "Buddy", dateOfBirth = Date(), type = PetType.DOG,
            breed = "Golden Retriever", color = "Yellow", weight = 40.0,
            gender = PetGender.MALE, sterilized = true)

        val pet2 = Pet(1002, "Sammy", dateOfBirth = Date(), type = PetType.DOG,
            breed = "Bulldog", color = "Red and White", weight = 50.0,
            gender = PetGender.FEMALE, sterilized = false)

        // Insert a pet
        petDao.insert(pet1)
        petDao.insert(pet2)

        // Verify only one entry exists
        val pets = petDao.getAll().first()
        assertEquals(2, pets.size)

        val petBuddy = pets[0]
        val petSammy = pets[1]

        assertEquals("Buddy", petBuddy.name)
        assertEquals("Golden Retriever", petBuddy.breed)
        assertEquals(40.0, petBuddy.weight)
        assertEquals(PetType.DOG, petBuddy.type)
        assertEquals(PetGender.MALE, petBuddy.gender)

        assertEquals("Sammy", petSammy.name)
        assertEquals(PetType.DOG, petSammy.type)
        assertEquals(PetGender.FEMALE, petSammy.gender)
        assertEquals("Bulldog", petSammy.breed)
        assertEquals(50.0, petSammy.weight)

        // Retrieve the pet by ID
        val retrievedPet1 = petDao.getPet(1001)
        assertNotNull(retrievedPet1)
        assertEquals(retrievedPet1.first().name, "Buddy")

        val retrievedPet2 = petDao.getPet(1002)
        assertNotNull(retrievedPet2)
        assertEquals(retrievedPet2.first().name, "Sammy")
    }

    @Test
    fun insertDuplicatePet() = runBlocking {
        // Insert a pet
        val pet = Pet(1001, "Buddy", dateOfBirth = Date(), type = PetType.DOG,
            breed = "Golden Retriever", color = "Yellow", weight = 40.0,
            gender = PetGender.MALE, sterilized = true)

        petDao.insert(pet)

        val pet2 = pet.copy(name = "Jimmy")

        petDao.insert(pet2)

        // Verify only one entry exists
        val pets = petDao.getAll().first()
        assertEquals(1, pets.size)
        assertEquals("Jimmy", pets[0].name)
    }

    @Test
    fun updatePet() = runBlocking {
        val pet = Pet(1001, "Buddy", dateOfBirth = Date(), type = PetType.DOG,
            breed = "Golden Retriever", color = "Yellow", weight = 40.0,
            gender = PetGender.MALE, sterilized = true)

        // Insert a pet
        petDao.insert(pet)

        val buddy = petDao.getPet(1001).first()
        assertNotNull(buddy)
        val updatedPet = buddy.copy(name = "Jimmy", color = "Black")
        petDao.update(updatedPet)

        val pet2 = petDao.getPet(1001).first()
        assertNotNull(pet2)
        assertEquals(pet2.name, "Jimmy")
        assertEquals(pet2.color, "Black")
    }

    @Test
    fun deletePet() = runBlocking {
        val pet = Pet(1001, "Buddy", dateOfBirth = Date(), type = PetType.DOG,
            breed = "Golden Retriever", color = "Yellow", weight = 40.0,
            gender = PetGender.MALE, sterilized = true)

        petDao.insert(pet)

        val pets = petDao.getAll().first()
        assertEquals(1, pets.size)

        petDao.delete(pets[0])

        val updatedPets = petDao.getAll().first()
        assertEquals(0, updatedPets.size)

        val pet2 = petDao.getPet(1001).first()
        assertNull(pet2)
    }

}