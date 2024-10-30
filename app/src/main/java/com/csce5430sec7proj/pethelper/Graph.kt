package com.csce5430sec7proj.pethelper

import android.content.Context
import com.csce5430sec7proj.pethelper.data.PetHelperDatabase
import com.csce5430sec7proj.pethelper.data.PetHelperRepository

object Graph {
    lateinit var db: PetHelperDatabase
        private set

    val repository by lazy {
        PetHelperRepository(
            appointmentDao = db.appointmentDao(),
            petDao = db.petDao(),
            recordDao = db.recordDao(),
            vaccinationDao = db.vaccinationDao(),
            vendorDao = db.vendorDao()
        )
    }

    fun provide(context: Context) {
        db = PetHelperDatabase.getDatabase(context)
    }
}