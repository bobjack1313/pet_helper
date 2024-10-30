@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.csce5430sec7proj.pethelper.ui.records

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch

// TODO: This should be merged into the functionality of the RecordsScreen
//@Composable
//fun MedicalRecordsScreen(
//    navController: NavController,
//    recordsViewModel: RecordsViewModel = viewModel()
//) {
//    // Scaffold provides the basic layout structure including TopAppBar and FloatingActionButton
//    Scaffold(
//        topBar = {
//            // TopAppBar displays the title of the screen
//            TopAppBar(title = { Text("Medical Records") })
//        },
//        floatingActionButton = {
//            // FloatingActionButton to trigger adding a new record
//            FloatingActionButton(onClick = {
//                if (!recordsViewModel.isAddDialogVisible.value) {
//                    recordsViewModel.showAddDialog()
//                }
//            }) {
//                Icon(Icons.Default.Add, contentDescription = "Add Record")
//            }
//        }
//    ) { padding ->
//        // Display a placeholder when there are no records
//        if (recordsViewModel.medicalRecords.isEmpty()) {
//            DisplayNoRecordsContent("Medical Record") {
//                if (!recordsViewModel.isAddDialogVisible.value) {
//                    recordsViewModel.showAddDialog()
//                }
//            }
//        } else {
//            // Display list of medical records
//            LazyColumn(
//                modifier = Modifier.padding(padding)
//            ) {
//                items(recordsViewModel.medicalRecords) { record ->
//                    Text("${record.title} - ${record.date}", modifier = Modifier.padding(8.dp))
//                }
//            }
//        }
//    }
//
//    // Display the AddMedicalRecordDialog if the dialog visibility state is true
//    if (recordsViewModel.isAddDialogVisible.value) {
//        AddMedicalRecordDialog(viewModel = recordsViewModel)
//    }
//}
//
//@Composable
//fun AddMedicalRecordDialog(viewModel: RecordsViewModel) {
//    var title by remember { mutableStateOf("") }
//    var date by remember { mutableStateOf("") }
//    val coroutineScope = rememberCoroutineScope()
//
//    // Dialog for adding a new medical record
//    Dialog(onDismissRequest = { viewModel.hideAddDialog() }) {
//        Surface(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            shape = MaterialTheme.shapes.medium
//        ) {
//            Column(
//                modifier = Modifier.padding(16.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                // Title of the dialog
//                Text(text = "Add New Record", style = MaterialTheme.typography.titleLarge)
//                // TextField for entering the title of the record
//                TextField(
//                    value = title,
//                    onValueChange = { title = it },
//                    label = { Text("Title") }
//                )
//                // TextField for entering the date of the record
//                TextField(
//                    value = date,
//                    onValueChange = { date = it },
//                    label = { Text("Date") }
//                )
//                // Row with Cancel and Save buttons
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.End
//                ) {
//                    // Cancel button to dismiss the dialog
//                    TextButton(onClick = { viewModel.hideAddDialog() }) {
//                        Text("Cancel")
//                    }
//                    // Save button to add the record to the database
//                    TextButton(onClick = {
//                        coroutineScope.launch {
//                            viewModel.addRecordToDatabase(title, date)
//                            viewModel.hideAddDialog()
//                        }
//                    }) {
//                        Text("Save")
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun DisplayNoRecordsContent(contentType: String, onAddClick: () -> Unit) {
//    // Displayed when there are no records available
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        // Text indicating that the content is empty
//        Text(text = "$contentType is empty")
//        Spacer(modifier = Modifier.height(16.dp))
//        // Button to add a new record
//        Button(onClick = onAddClick) {
//            Text(text = "Add Record")
//        }
//    }
//}
