package com.csce5430sec7proj.pethelper.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.csce5430sec7proj.pethelper.ui.pets.PetsScreen
import com.csce5430sec7proj.pethelper.ui.pets.PetDetailsScreen

@Composable
fun PetsNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "pets_screen",
        modifier = modifier
    ) {
        composable("pets_screen") {
            PetsScreen(
                onNavigateInLine = { petId ->
                    navController.navigate("pet_detail_screen/$petId")
                },
                modifier = Modifier,
                onNavigate = {   }
            )
        }
        composable("pet_detail_screen/{petId}") { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId")
            PetDetailsScreen(
                petId = petId ?: "",
                modifier = Modifier,
                onNavigate = { /* Define back navigation or any other action */ }
            )
        }
    }
}