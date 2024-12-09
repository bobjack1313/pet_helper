package com.csce5430sec7proj.pethelper.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.csce5430sec7proj.pethelper.ui.records.RecordDetailScreen
import com.csce5430sec7proj.pethelper.ui.records.RecordsScreen
import com.csce5430sec7proj.pethelper.ui.records.PDFPickerScreen
import com.csce5430sec7proj.pethelper.ui.records.RecordAddEditScreen


@Composable
fun RecordsNavHost(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "records_screen",
        modifier = modifier
    ) {
        composable("records_screen") {
            RecordsScreen(
                modifier = modifier,
                onNavigateDetail = { recordId: Int ->
                    navController.navigate("record_detail_screen/$recordId")
                },
                onNavigate = {
                    navController.navigate("record_edit_screen")
                }
            )
        }

        composable("record_edit_screen") {
            RecordAddEditScreen(
                navController = navController,
                recordId = null,
                onNavigateBack = { navController.popBackStack() },
            )
        }

        composable("record_edit_screen/{recordId}") { backStackEntry ->
            val recordId = backStackEntry.arguments?.getString("recordId")?.toInt()
            RecordAddEditScreen(
                navController = navController,
                recordId = recordId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("pdf_picker_screen") {
            PDFPickerScreen(
                onNavigateBack = { navController.popBackStack() },
                onPdfSelected = { uri ->
                    uri?.let {
                        Toast.makeText(context, "PDF Selected: ${it.path}",
                            Toast.LENGTH_SHORT).show()
                        // Handle the selected PDF
                        // TODO
                    }
                    navController.popBackStack()
                }
            )
        }

        // Record Details Screen
        composable(
            route = "record_detail_screen/{recordId}",
            arguments = listOf(navArgument("recordId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recordId = backStackEntry.arguments?.getInt("recordId")
            RecordDetailScreen(
                recordId = recordId,
                onNavigate = { id -> navController.navigate("record_edit_screen/$id") },
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
