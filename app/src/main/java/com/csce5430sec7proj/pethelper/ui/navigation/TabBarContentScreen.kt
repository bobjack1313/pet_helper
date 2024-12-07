package com.csce5430sec7proj.pethelper.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.csce5430sec7proj.pethelper.ui.notifications.NotificationsScreen
import com.csce5430sec7proj.pethelper.ui.pets.PetsScreen
import com.csce5430sec7proj.pethelper.ui.records.RecordsScreen
import com.csce5430sec7proj.pethelper.ui.settings.SettingsScreen
import androidx.navigation.NavController


@Composable
fun TabBarContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onNavigate: (Int) -> Unit,
    navController: NavController  
) {
    when (selectedIndex) {
        0 -> PetsScreen(
            onNavigate = onNavigate,
            onNavigateDetail = { /* petId -> Handle detail navigation */ },
            modifier = Modifier,
        )
        1 -> RecordsScreen(
            navController = navController,
            )
        2 -> NotificationsScreen()
        3 -> SettingsScreen()
    }
}