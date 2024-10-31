@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.csce5430sec7proj.pethelper.ui.records

import androidx.compose.foundation.clickable
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
import com.csce5430sec7proj.pethelper.data.entities.Record
import com.csce5430sec7proj.pethelper.data.entities.RecordType
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
                viewModel = recordsViewModel,
                recordType = tabs[selectedTabIndex]
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

    Dialog(onDismissRequest = { viewModel.hideAddDialog() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
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
                TextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date") }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { viewModel.hideAddDialog() }) { Text("Cancel") }
                    TextButton(onClick = {
                        coroutineScope.launch {
                            viewModel.addRecord(
                                Record(
                                    type = recordType,  // 使用当前选中的 RecordType
                                    description = description,
                                    date = null,  // 可根据需要设置实际日期
                                    petIdFk = 0,
                                    vendorIdFk = null,
                                    cost = 0.0
                                )
                            )
                            viewModel.hideAddDialog()
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
    viewModel: RecordsViewModel,
    recordType: RecordType
) {
    val records = viewModel.getFilteredRecords()

    if (records.isEmpty()) {
        EmptyContent(recordType.name.replace("_", " ")) { viewModel.showAddDialog() }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(records) { record ->
                ListItem(
                    headlineContent = { Text(record.description ?: "No Description") },
                    supportingContent = { Text("Date: ${record.date ?: "No Date"}") },
                    trailingContent = {
                        IconButton(onClick = { viewModel.deleteRecord(record) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Record")
                        }
                    },
                    modifier = Modifier.clickable {
                        navController.navigate("record_detail_screen/${recordType.name}/${record.id}")
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