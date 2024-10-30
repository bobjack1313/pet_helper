package com.csce5430sec7proj.pethelper.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.res.stringResource
import com.csce5430sec7proj.pethelper.R

// Main navigation screen of the app with tab bar
@Composable
fun MainNavScreen(modifier: Modifier = Modifier) {

    // List of navigation items with labels, icons, and badge count
    var notificationsBadgeCount by remember { mutableIntStateOf(5) }

    val tabItemList = listOf(
        NavTabItem(stringResource(id = R.string.tab_bar_pets), Icons.Default.AccountCircle, 0),
        NavTabItem(stringResource(id = R.string.tab_bar_records), Icons.Default.Create, 0),
        NavTabItem(stringResource(id = R.string.tab_bar_notifications), Icons.Default.Notifications, notificationsBadgeCount),
        NavTabItem(stringResource(id = R.string.tab_bar_settings), Icons.Default.Settings, 0),
    )
    
    // State to track the selected tab index
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                tabItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                        },
                        icon = {
                            BadgedBox(badge = {
                                if (navItem.badgeCount > 0) {
                                    Badge {
                                        Text(text = navItem.badgeCount.toString())
                                    }
                                }
                            }) {
                                Icon(imageVector = navItem.icon, contentDescription = "Icon")
                            }
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // Display content based on selected tab
        when (selectedTabIndex) {
            0 -> PetsNavHost(modifier = Modifier.padding(innerPadding))
            1 -> RecordsNavHost(modifier = Modifier.padding(innerPadding))
            2 -> NotificationsNavHost(modifier = Modifier.padding(innerPadding))
            3 -> SettingsNavHost(modifier = Modifier.padding(innerPadding))
        }
    }
}
