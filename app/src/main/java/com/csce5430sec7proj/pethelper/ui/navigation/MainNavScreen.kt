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

// TODO: Lets animate the tab bar items when selected
// https://proandroiddev.com/jetpack-compose-tutorial-animated-navigation-bar-354411c679c8

// Main navigation screen of the app with tab bar
@Composable
fun MainNavScreen(modifier: Modifier = Modifier) {

    // List of navigation items with labels, icons, and badge count
    var notificationsBadgeCount by remember { mutableIntStateOf(5) }

    val tabItemList = listOf(
        NavTabItem("Pets", Icons.Default.AccountCircle,0),
        NavTabItem("Records", Icons.Default.Create, 0),
        NavTabItem("Notifications", Icons.Default.Notifications, notificationsBadgeCount),
        NavTabItem("Settings", Icons.Default.Settings,0),
    )
    // State to track the selected tab index
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                tabItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected =  selectedTabIndex == index ,
                        onClick = {
                            selectedTabIndex = index
                        },
                        icon = {
                            BadgedBox(badge = {
                                if(navItem.badgeCount>0)
                                    Badge(){
                                        Text(text = navItem.badgeCount.toString())
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
        TabBarContentScreen(modifier = Modifier.padding(innerPadding),selectedTabIndex)
    }
}
