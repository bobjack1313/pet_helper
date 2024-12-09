package com.csce5430sec7proj.pethelper.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.csce5430sec7proj.pethelper.ui.services.ContactServiceScreen
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
        // Services Screen
        composable("services_screen") {
            ServicesScreen(
                onNavigateDetail = { serviceId: Int ->
                    navController.navigate("service_detail_screen/$serviceId")
                },
                onNavigateEdit = {
                    navController.navigate("service_edit_screen")
                },
                onNavigateContact = { serviceId: Int ->
                    navController.navigate("contact_service/$serviceId")
                }
            )
        }

        // Add/Edit Service Screen
        composable("service_edit_screen") {
            ServiceAddEditScreen(
                serviceId = null,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("service_edit_screen/{serviceId}") { backStackEntry ->
            val serviceId = backStackEntry.arguments?.getString("serviceId")?.toInt()
            ServiceAddEditScreen(
                serviceId = serviceId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Service Details Screen
        composable(
            route = "service_detail_screen/{serviceId}",
            arguments = listOf(navArgument("serviceId") { type = NavType.IntType })
        ) { backStackEntry ->
            val serviceId = backStackEntry.arguments?.getInt("serviceId")
            ServiceDetailsScreen(
                serviceId = serviceId,
                onNavigateEdit = { id ->
                    navController.navigate("service_edit_screen/$id")
                },
                onNavigateContact = { id ->
                    navController.navigate("contact_service/$id")
                },
                onNavigateBack = { navController.popBackStack() },
            )
        }

        // Contact Service Screen
        composable(
            route = "contact_service/{serviceId}",
            arguments = listOf(navArgument("serviceId") { type = NavType.IntType })
        ) { backStackEntry ->
            val serviceId = backStackEntry.arguments?.getInt("serviceId")
            ContactServiceScreen(
                serviceId = serviceId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
