package com.csce5430sec7proj.pethelper.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.csce5430sec7proj.pethelper.ui.notifications.NotificationsScreen
import com.csce5430sec7proj.pethelper.ui.pets.PetsScreen
import com.csce5430sec7proj.pethelper.ui.records.RecordsScreen
import com.csce5430sec7proj.pethelper.ui.settings.SettingsScreen

@Composable
fun TabBarContentScreen(modifier: Modifier = Modifier, selectedIndex: Int, onNavigate: (Int) -> Unit) {
    when (selectedIndex) {
        0 -> PetsScreen(
            onNavigate = onNavigate,
            modifier = Modifier,
            onNavigateInLine = {  }
        )
        1 -> RecordsScreen(onNavigate = { /* Handle navigation */ })
        2 -> NotificationsScreen()
        3 -> SettingsScreen()
    }
}