package com.csce5430sec7proj.pethelper.data

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room
import com.csce5430sec7proj.pethelper.data.daos.PetDao
import com.csce5430sec7proj.pethelper.data.entities.Pet

/** PetHelperDatabase.kt
** This file defines the Room database for the Pet Helper application.
** It contains the PetDao interface and the PetHelperDatabase class.
** Add database entities below
*/
@Database(entities = [Pet::class],
    version = 1, exportSchema = false)
abstract class PetHelperDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
    // Add other DAOs as needed

    companion object {
        @Volatile
        private var Instance: PetHelperDatabase? = null

        fun getDatabase(context: Context): PetHelperDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, PetHelperDatabase::class.java, "pethelper_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}