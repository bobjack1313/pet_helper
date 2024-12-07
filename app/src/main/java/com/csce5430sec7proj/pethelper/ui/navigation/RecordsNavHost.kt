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
import com.csce5430sec7proj.pethelper.data.entities.RecordType
import com.csce5430sec7proj.pethelper.ui.records.RecordDetailScreen
import com.csce5430sec7proj.pethelper.ui.records.RecordsScreen
import com.csce5430sec7proj.pethelper.ui.records.RecordsViewModel
import com.csce5430sec7proj.pethelper.ui.records.PDFPickerScreen
import com.csce5430sec7proj.pethelper.ui.records.RecordAddEditScreen


@Composable
fun RecordsNavHost(
    modifier: Modifier = Modifier,
    recordsViewModel: RecordsViewModel
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
                navController = navController,
                recordsViewModel = recordsViewModel,
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

        composable(
            route = "record_detail_screen/{recordId}/{recordType}",
            arguments = listOf(
                navArgument("recordId") { type = NavType.IntType },
                navArgument("recordType") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val recordId = backStackEntry.arguments?.getInt("recordId") ?: 0
            val recordTypeString = backStackEntry.arguments?.getString(
                "recordType") ?: "MEDICAL"
            val recordType = try {
                RecordType.valueOf(recordTypeString)
            } catch (e: IllegalArgumentException) {
                RecordType.MEDICAL
            }

            RecordDetailScreen(
                navController = navController,
                recordId = recordId,
                recordType = recordType,
                recordsViewModel = recordsViewModel
            )
        }
    }
}
