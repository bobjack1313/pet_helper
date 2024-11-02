package com.csce5430sec7proj.pethelper.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.csce5430sec7proj.pethelper.data.daos.RecordDao
import com.csce5430sec7proj.pethelper.data.entities.RecordType
import com.csce5430sec7proj.pethelper.ui.records.RecordDetailScreen
import com.csce5430sec7proj.pethelper.ui.records.RecordsScreen
import com.csce5430sec7proj.pethelper.ui.records.RecordsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RecordsNavHost(
    modifier: Modifier = Modifier,
    recordsViewModel: RecordsViewModel // 添加 RecordsViewModel 作为参数
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "records_screen",
        modifier = modifier
    ) {
        composable("records_screen") {
            RecordsScreen(
                navController = navController,
                recordsViewModel = recordsViewModel, // 使用传入的 ViewModel 实例
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(
            route = "record_detail_screen/{recordId}/{recordType}",
            arguments = listOf(
                navArgument("recordId") { type = NavType.IntType },
                navArgument("recordType") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val recordId = backStackEntry.arguments?.getInt("recordId") ?: 0
            val recordTypeString = backStackEntry.arguments?.getString("recordType") ?: "MEDICAL"
            val recordType = try {
                RecordType.valueOf(recordTypeString)
            } catch (e: IllegalArgumentException) {
                RecordType.MEDICAL // 默认值
            }

            RecordDetailScreen(
                navController = navController,
                recordId = recordId,
                recordType = recordType,
                recordsViewModel = recordsViewModel // 使用传入的 ViewModel 实例
            )
        }
    }
}
