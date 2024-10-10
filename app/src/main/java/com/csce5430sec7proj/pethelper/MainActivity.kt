package com.csce5430sec7proj.pethelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.csce5430sec7proj.pethelper.ui.navigation.MainNavScreen
import com.csce5430sec7proj.pethelper.ui.theme.PetHelperTheme


// Entry Point of the app
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetHelperTheme {
                // Main content of the app with tab bar
                MainNavScreen()
            }
        }
    }
}