package com.csce5430sec7proj.pethelper

import android.app.Application
import android.util.Log


class PetHelperApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("PetHelperApp", "Application started")
    }
}