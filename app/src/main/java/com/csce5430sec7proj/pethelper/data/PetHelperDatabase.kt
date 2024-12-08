package com.csce5430sec7proj.pethelper.data

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room
import androidx.room.TypeConverters
import com.csce5430sec7proj.pethelper.data.daos.AppointmentDao
import com.csce5430sec7proj.pethelper.data.daos.PetDao
import com.csce5430sec7proj.pethelper.data.entities.Pet
import com.csce5430sec7proj.pethelper.data.daos.RecordDao
import com.csce5430sec7proj.pethelper.data.daos.VaccinationDao
import com.csce5430sec7proj.pethelper.data.daos.ServiceDao
import com.csce5430sec7proj.pethelper.data.entities.Appointment
import com.csce5430sec7proj.pethelper.data.entities.Record
import com.csce5430sec7proj.pethelper.data.entities.Service
import com.csce5430sec7proj.pethelper.data.entities.Vaccination

/** PetHelperDatabase.kt
** This file defines the Room database for the Pet Helper application.
** It contains the PetDao interface and the PetHelperDatabase class.
** Add database entities below
*/

@TypeConverters(value = [Converters::class])
@Database(entities = [Pet::class, Record::class, Appointment::class, Service::class,
                     Vaccination::class],
    version = 3, exportSchema = false)
abstract class PetHelperDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
    abstract fun recordDao(): RecordDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun serviceDao(): ServiceDao
    abstract fun vaccinationDao(): VaccinationDao
    // Add other DAOs as needed

    companion object {
        @Volatile
        private var Instance: PetHelperDatabase? = null

        fun getDatabase(context: Context): PetHelperDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    PetHelperDatabase::class.java,
                    // This causes data loss, a migration strategy may be needed
                    "pethelper-db").fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}