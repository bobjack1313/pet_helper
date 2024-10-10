package com.csce5430sec7proj.pethelper.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavTabItem(
    val label : String,
    val icon : ImageVector,
    val badgeCount : Int,
)