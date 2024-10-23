package com.csce5430sec7proj.pethelper.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.csce5430sec7proj.pethelper.ui.notifications.NotificationsScreen

@Composable
fun NotificationsNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "notifications_screen",
        modifier = modifier
    ) {
        composable("notifications_screen") {
            NotificationsScreen()
        }
    }
}