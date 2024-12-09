package com.csce5430sec7proj.pethelper.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.csce5430sec7proj.pethelper.ui.services.ServiceAddEditScreen
import com.csce5430sec7proj.pethelper.ui.services.ServicesScreen
import com.csce5430sec7proj.pethelper.ui.services.ServiceDetailsScreen


@Composable
fun ServicesNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "services_screen",
        modifier = modifier
    ) {
        composable("services_screen") {
            ServicesScreen(
                onNavigateDetail = { serviceId: Int ->
                    navController.navigate("service_detail_screen/$serviceId")
                },
                onNavigate = {
                    navController.navigate("service_edit_screen")
                }
            )
        }
        composable("service_edit_screen") {
            ServiceAddEditScreen(
                serviceId = null,
                onNavigateBack = { navController.popBackStack() },
            )
        }
        composable("service_edit_screen/{serviceId}") { backStackEntry ->
            val serviceId = backStackEntry.arguments?.getString("serviceId")?.toInt()
            ServiceAddEditScreen(
                serviceId = serviceId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Pet Details Screen
        composable(
            route = "service_detail_screen/{serviceId}",
            arguments = listOf(navArgument("serviceId") { type = NavType.IntType })
        ) { backStackEntry ->
            val serviceId = backStackEntry.arguments?.getInt("serviceId")
            ServiceDetailsScreen(
                serviceId = serviceId,
                onNavigate = { id -> navController.navigate("service_edit_screen/$id") },
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}