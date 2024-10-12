package com.csce5430sec7proj.pethelper

import android.content.Context
import com.csce5430sec7proj.pethelper.data.PetHelperDatabase
import com.csce5430sec7proj.pethelper.data.PetRepository

object Graph {
    lateinit var db: PetHelperDatabase
        private set

    val repository by lazy {
        PetRepository(
            appointmentDao = db.appointmentDao(),
            petDao = db.petDao(),
            recordDao = db.recordDao(),
            vendorDao = db.vendorDao()
        )
    }

    fun provide(context: Context) {
        db = PetHelperDatabase.getDatabase(context)
    }
}