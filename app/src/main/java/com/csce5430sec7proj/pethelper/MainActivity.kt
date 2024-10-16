package com.csce5430sec7proj.pethelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.csce5430sec7proj.pethelper.data.PetHelperDatabase
import com.csce5430sec7proj.pethelper.ui.navigation.MainNavScreen
import com.csce5430sec7proj.pethelper.ui.theme.PetHelperTheme


 //Entry Point of the app
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
        Graph.provide(this)

        setContent {
            PetHelperTheme {
                // Main content of the app with tab bar
                MainNavScreen()
            }
        }
    }
}

//
//import com.google.android.material.bottomnavigation.BottomNavigationView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.navigation.findNavController
//import androidx.navigation.ui.AppBarConfiguration
//import androidx.navigation.ui.setupActionBarWithNavController
//import androidx.navigation.ui.setupWithNavController
//import com.csce5430sec7proj.pethelper.databinding.ActivityMainBinding
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//    }
//}
