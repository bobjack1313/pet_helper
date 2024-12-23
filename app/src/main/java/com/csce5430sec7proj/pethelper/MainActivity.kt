package com.csce5430sec7proj.pethelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.csce5430sec7proj.pethelper.data.PetHelperDatabase
import com.csce5430sec7proj.pethelper.ui.navigation.MainNavScreen
import com.csce5430sec7proj.pethelper.ui.theme.PetHelperTheme

// Entry Point of the app
class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            PetHelperDatabase::class.java,
            name = "pethelper-db"
        ).build()
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Graph if needed (optional, depending on your setup)
        Graph.provide(this)

        setContent {
            PetHelperTheme(dynamicColor = false) {
                // Pass the recordDao from the database to MainNavScreen
                MainNavScreen()
            }
        }
    }
}
