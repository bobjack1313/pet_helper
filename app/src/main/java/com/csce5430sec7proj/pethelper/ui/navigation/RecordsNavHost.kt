package com.csce5430sec7proj.pethelper.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.csce5430sec7proj.pethelper.ui.records.RecordsScreen
import com.csce5430sec7proj.pethelper.data.PetHelperDatabase

@Composable
fun RecordsNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val db = PetHelperDatabase.getDatabase(context)
    val medicalRecordDao = db.medicalRecordDao() // 获取 MedicalRecordDao 实例

    NavHost(
        navController = navController,
        startDestination = "records_screen",
        modifier = modifier
    ) {
        composable("records_screen") {
            RecordsScreen(
                navController = navController,
                medicalRecordDao = medicalRecordDao,  // 传递 medicalRecordDao 实例
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }
    }
}
