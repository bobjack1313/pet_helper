package com.csce5430sec7proj.pethelper.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.csce5430sec7proj.pethelper.ui.records.RecordsScreen

@Composable
fun RecordsNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "records_screen",
        modifier = modifier
    ) {
        composable("records_screen") {
            RecordsScreen(
                navController = navController,  
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        // TODO: Records navigation needs built out for each of the tabs, i think
    }
}