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
import androidx.compose.foundation.clickable
import com.csce5430sec7proj.pethelper.data.PetHelperDatabase
import com.csce5430sec7proj.pethelper.data.daos.MedicalRecordDao

@Composable
fun RecordsScreen(
    navController: NavController,
    medicalRecordDao: MedicalRecordDao, // 将 DAO 传递给 Composable
    onNavigate: (String) -> Unit = {}
) {
    val viewModelFactory = RecordsViewModelFactory(medicalRecordDao)
    val recordsViewModel: RecordsViewModel = viewModel(factory = viewModelFactory)

    val selectedTabIndex by recordsViewModel.selectedTabIndex
    val tabs = listOf("Medical Record", "Vaccinations", "Dewormings", "Physical Exams")

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Medical Records") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (!recordsViewModel.isAddDialogVisible.value) {
                    recordsViewModel.showAddDialog()
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Record")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // TabRow 用于显示顶部选项卡
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { recordsViewModel.setSelectedTab(index) },
                        text = { Text(title) }
                    )
                }
            }
            // 根据选中的标签展示不同的内容
            when (selectedTabIndex) {
                0 -> MedicalRecordContent(navController, recordsViewModel)
                // 其他标签可以在此添加
            }
        }
    }

    if (recordsViewModel.isAddDialogVisible.value) {
        AddRecordDialog(viewModel = recordsViewModel)
    }
}

@Composable
fun AddRecordDialog(viewModel: RecordsViewModel) {
    var title by remember { mutableStateOf("") }
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
                Text(text = "Add New Record", style = MaterialTheme.typography.titleLarge)
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
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
                    TextButton(onClick = { viewModel.hideAddDialog() }) {
                        Text("Cancel")
                    }
                    TextButton(onClick = {
                        coroutineScope.launch {
                            viewModel.addRecordToDatabase(title, date)
                            viewModel.hideAddDialog()
                        }
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Composable
fun MedicalRecordContent(navController: NavController, viewModel: RecordsViewModel) {
    val medicalRecords = viewModel.medicalRecords
    if (medicalRecords.isEmpty()) {
        NoRecordsContent("Medical Record") {
            if (!viewModel.isAddDialogVisible.value) {
                viewModel.showAddDialog()
            }
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(medicalRecords) { record ->
                ListItem(
                    headlineContent = { Text(record.title) },
                    supportingContent = { Text("Date: ${record.date}") },
                    modifier = Modifier.clickable {
                        navController.navigate("medical_record_details/${record.title}")
                    }
                )
                Divider()
            }
        }
    }
}

@Composable
fun NoRecordsContent(contentType: String, onAddClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "$contentType is empty")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAddClick) {
            Text(text = "Add Record")
        }
    }
}
