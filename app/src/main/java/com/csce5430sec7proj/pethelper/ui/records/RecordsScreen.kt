@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.csce5430sec7proj.pethelper.ui.records

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.platform.LocalContext
import com.csce5430sec7proj.pethelper.data.entities.Record
import com.csce5430sec7proj.pethelper.data.entities.RecordType
import java.text.SimpleDateFormat
import java.util.*
import android.util.Log
import androidx.compose.foundation.clickable

@Composable
fun RecordsScreen(
    navController: NavController,
    recordsViewModel: RecordsViewModel = viewModel(),
    onNavigate: (String) -> Unit = {}
) {
    val recordsState by recordsViewModel.state.collectAsState()
    val selectedTabIndex by recordsViewModel.selectedTabIndex
    val tabs = listOf(
        RecordType.MEDICAL,
        RecordType.GROOMING,
        RecordType.VACCINATION,
        RecordType.TRAINING,
        RecordType.DIET
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("Pet Records") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { recordsViewModel.showAddDialog() }) {
                Icon(Icons.Default.Add, contentDescription = "Add Record")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, type ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { recordsViewModel.setSelectedTab(index) },
                        text = { Text(type.name.replace("_", " ")) }
                    )
                }
            }
            RecordContent(
                navController = navController,
                recordsViewModel = recordsViewModel,
                records = recordsViewModel.getFilteredRecords()
            )
        }
    }

    if (recordsState.isAddDialogVisible) {
        AddRecordDialog(viewModel = recordsViewModel, recordType = tabs[selectedTabIndex])
    }
}

@Composable
fun AddRecordDialog(viewModel: RecordsViewModel, recordType: RecordType) {
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    
    val calendar = Calendar.getInstance()
    val context = LocalContext.current

    Dialog(onDismissRequest = { viewModel.hideAddDialog() }) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Add New ${recordType.name.replace("_", " ")} Record", style = MaterialTheme.typography.titleLarge)
                
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                
                OutlinedButton(
                    onClick = {
                        Log.d("DatePicker", "Date Picker Dialog triggered")
                        try {
                            DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    calendar.set(year, month, dayOfMonth)
                                    date = dateFormat.format(calendar.time)
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            ).show()
                        } catch (e: Exception) {
                            Log.e("DatePicker", "Error showing Date Picker Dialog", e)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (date.isNotBlank()) date else "Select Date")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { viewModel.hideAddDialog() }) { Text("Cancel") }
                    TextButton(onClick = {
                        if (description.isNotBlank() && date.isNotBlank()) {
                            try {
                                val parsedDate = dateFormat.parse(date)
                                coroutineScope.launch {
                                    viewModel.addRecord(
                                        Record(
                                            type = recordType,
                                            description = description,
                                            date = parsedDate,
                                            petIdFk = 0,
                                            vendorIdFk = null,
                                            cost = 0.0
                                        )
                                    )
                                    viewModel.hideAddDialog()
                                }
                            } catch (e: Exception) {
                                Log.e("AddRecord", "Error adding record", e)
                            }
                        }
                    }) { Text("Save") }
                }
            }
        }
    }
}

@Composable
fun RecordContent(
    navController: NavController,
    recordsViewModel: RecordsViewModel,
    records: List<Record>
) {
    if (records.isEmpty()) {
        EmptyContent("No Records Available") {
            recordsViewModel.showAddDialog()
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(records) { record ->
                ListItem(
                    headlineContent = { Text(record.description ?: "No Description") },
                    supportingContent = { Text("Date: ${record.date?.let { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it) } ?: "No Date"}") },
                    trailingContent = {
                        IconButton(onClick = {
                            recordsViewModel.deleteRecord(record)
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Record")
                        }
                    },
                    modifier = Modifier.clickable {
                        navController.navigate("record_detail_screen/${record.id}/${record.type.name}")
                    }
                )
                Divider()
            }
        }
    }
}


@Composable
fun EmptyContent(contentType: String, onAddClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("$contentType is empty")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAddClick) { Text("Add Record") }
    }
}
