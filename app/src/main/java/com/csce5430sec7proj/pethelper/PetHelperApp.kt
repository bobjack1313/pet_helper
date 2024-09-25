package com.csce5430sec7proj.pethelper

import android.app.Application
import android.util.Log
import com.dbflow5.config.FlowManager


class PetHelperApp : Application() {
    override fun onCreate() {
        super.onCreate()
        //init(Builder(this).build())
        FlowManager.init(this)
        // Uncomment if we want to open a DB immediately
//            .openDatabasesOnInit(true)
//            .build())
        Log.d("PetHelperApp", "Application started")
    }
}