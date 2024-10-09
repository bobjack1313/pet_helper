package com.csce5430sec7proj.pethelper

import android.app.Application
import android.util.Log


class PetHelperApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("PetHelperApp", "Application started")
    }
}