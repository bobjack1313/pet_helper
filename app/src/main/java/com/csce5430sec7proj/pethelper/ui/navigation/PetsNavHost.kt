package com.csce5430sec7proj.pethelper.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.csce5430sec7proj.pethelper.ui.pets.PetAddEditScreen
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
                onNavigateDetail = { petId: Int ->
                    navController.navigate("pet_detail_screen/$petId")
                },
                onNavigate = {
                    navController.navigate("pet_edit_screen")
                }
            )
        }
        composable("pet_edit_screen") {
            PetAddEditScreen(
                petId = null,
                onNavigateBack = { navController.popBackStack() },
            )
        }
        composable("pet_edit_screen/{petId}") { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId")?.toInt()
            PetAddEditScreen(
                petId = petId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Pet Details Screen
        composable(
            route = "pet_detail_screen/{petId}",
            arguments = listOf(navArgument("petId") { type = NavType.IntType })
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getInt("petId")
            PetDetailsScreen(
                petId = petId,
                onNavigate = { id -> navController.navigate("pet_edit_screen/$id") },
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}