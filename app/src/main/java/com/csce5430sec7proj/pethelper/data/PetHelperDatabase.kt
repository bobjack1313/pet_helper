package com.csce5430sec7proj.pethelper.data

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.csce5430sec7proj.pethelper.data.converters.DateConverter
import com.csce5430sec7proj.pethelper.data.daos.AppointmentDao
import com.csce5430sec7proj.pethelper.data.daos.PetDao
import com.csce5430sec7proj.pethelper.data.daos.RecordDao
import com.csce5430sec7proj.pethelper.data.daos.VendorDao
import com.csce5430sec7proj.pethelper.data.daos.MedicalRecordDao
import com.csce5430sec7proj.pethelper.data.entities.Pet
import com.csce5430sec7proj.pethelper.data.entities.Record
import com.csce5430sec7proj.pethelper.data.entities.Appointment
import com.csce5430sec7proj.pethelper.data.entities.Vendor
import com.csce5430sec7proj.pethelper.data.entities.MedicalRecord

/** PetHelperDatabase.kt
 ** This file defines the Room database for the Pet Helper application.
 ** It contains the PetDao interface and the PetHelperDatabase class.
 ** Add database entities below
 */

// Tutorial https://www.youtube.com/watch?v=voMTReNRvUA

@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [Pet::class, Record::class, Appointment::class, Vendor::class, MedicalRecord::class],
    version = 2,  // 更新版本号
    exportSchema = false
)
abstract class PetHelperDatabase : RoomDatabase() {

    abstract fun petDao(): PetDao
    abstract fun recordDao(): RecordDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun vendorDao(): VendorDao
    abstract fun medicalRecordDao(): MedicalRecordDao

    // abstract fun vaccinationDao(): VaccinationDao
    // abstract fun dewormingDao(): DewormingDao
    // abstract fun physicalExamDao(): PhysicalExamDao

    companion object {
        @Volatile
        private var Instance: PetHelperDatabase? = null

        // 迁移逻辑，添加新表 medical_records
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // 创建新的 medical_records 表
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `medical_records` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `title` TEXT NOT NULL,
                        `date` TEXT NOT NULL
                    )
                """.trimIndent())
            }
        }

        fun getDatabase(context: Context): PetHelperDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    PetHelperDatabase::class.java,
                    "pethelper-db"
                )
                .addMigrations(MIGRATION_1_2)  // 添加迁移逻辑
                .build().also { Instance = it }
            }
        }
    }
}
