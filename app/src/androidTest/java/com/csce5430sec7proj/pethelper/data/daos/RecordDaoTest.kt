package com.csce5430sec7proj.pethelper.data.daos

import com.csce5430sec7proj.pethelper.data.PetHelperDatabase
import com.csce5430sec7proj.pethelper.data.entities.Record
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ApplicationProvider
import com.csce5430sec7proj.pethelper.data.entities.RecordType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date


@RunWith(AndroidJUnit4::class)
class RecordDaoTest {

    private lateinit var database: PetHelperDatabase
    private lateinit var recordDao: RecordDao

    @Before
    fun setup() {
        // Initialize in-memory database
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PetHelperDatabase::class.java
        ).allowMainThreadQueries().build()

        recordDao = database.recordDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndRetrieveRecord() = runBlocking {
        val record1 = Record(
            id = 1001,
            petIdFk = 1,
            type = RecordType.MEDICAL,
            date = Date(),
            vendorIdFk = 1,
            description = "Health check",
            cost = 100.00
        )

        val record2 = Record(
            id = 1002,
            petIdFk = 1,
            type = RecordType.TRAINING,
            date = Date(),
            vendorIdFk = 2,
            description = "Training program",
            cost = 200.00
        )

        // Insert records
        recordDao.insert(record1)
        recordDao.insert(record2)

        // Retrieve all records
        val records = recordDao.getAll().first()
        assertEquals(2, records.size)

        val retrievedRecord1 = records[0]
        val retrievedRecord2 = records[1]

        assertEquals(1001, retrievedRecord1.id)
        assertEquals(RecordType.MEDICAL, retrievedRecord1.type)
        assertEquals(100.00, retrievedRecord1.cost)
        assertEquals(1, retrievedRecord1.petIdFk)
        assertEquals(1, retrievedRecord1.vendorIdFk)


        assertEquals(200.00, retrievedRecord2.cost)
        assertEquals(1, retrievedRecord2.petIdFk)
        assertEquals(2, retrievedRecord2.vendorIdFk)
        assertEquals(RecordType.TRAINING, retrievedRecord2.type)
    }

    @Test
    fun updateRecord() = runBlocking {
        val record = Record(
            id = 1001,
            petIdFk = 1,
            type = RecordType.MEDICAL,
            date = Date(),
            vendorIdFk = 1,
            description = "Health check",
            cost = 100.00
        )

        // Insert record
        recordDao.insert(record)

        // Update record details
        val updatedRecord = record.copy(
            type = RecordType.TRAINING,
        )
        recordDao.update(updatedRecord)

        // Retrieve and verify update
        val retrievedRecord = recordDao.getRecord(updatedRecord.id).first()
        assertNotNull(retrievedRecord)
        assertEquals(RecordType.TRAINING, retrievedRecord.type)
    }

    @Test
    fun deleteRecord() = runBlocking {
        val record = Record(
            id = 1001,
            petIdFk = 1,
            type = RecordType.MEDICAL,
            date = Date(),
            vendorIdFk = 1,
            description = "Health check",
            cost = 100.00
        )
        // Insert record
        recordDao.insert(record)

        // Verify it exists
        val records = recordDao.getAll().first()
        assertEquals(1, records.size)

        // Delete the record
        recordDao.delete(records[0])

        // Verify deletion
        val updatedRecords = recordDao.getAll().first()
        assertEquals(0, updatedRecords.size)

        val retrievedRecord = recordDao.getRecord(record.id).first()
        assertNull(retrievedRecord)
    }
}
