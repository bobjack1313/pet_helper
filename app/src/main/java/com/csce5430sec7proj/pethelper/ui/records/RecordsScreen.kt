package com.csce5430sec7proj.pethelper.ui.records

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordsScreen(
    onNavigate: (String) -> Unit = {}
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Medical Record", "Vaccinations", "Dewormings", "Physical Exams")
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Medical Records") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
//                if (!recordsViewModel.isAddDialogVisible.value) {
//                    recordsViewModel.showAddDialog()
//                when (selectedTabIndex) {
//                    0 -> records
//                    1 -> vaccinations//.add("New Vaccination Record")
//                    2 -> training//.add("New Training Record")
//                    3 -> dietary//.add("Dietary Record")
//                }
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Record")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // TabRow 用于显示顶部选项卡
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
//                    Tab(
//                        selected = selectedTabIndex == index,
//                        onClick = { //recordsViewModel.setSelectedTab(index) },
//                        text = { Text(title) }
//                    )
//                }
            }
            // 根据选中的标签展示不同的内容
//            when (selectedTabIndex) {
//                0 -> MedicalRecordContent(navController, recordsViewModel)
//                0 -> RecordContent(navController)
//                1 -> VaccinationContent(navController)
//                2 -> TrainingContent(navController)
//                3 -> DietaryContent(navController)
//            }
        }
    }

//    if (recordsViewModel.isAddDialogVisible.value) {
//        AddRecordDialog(viewModel = recordsViewModel)
//    }
}

//val records = mutableStateOf<String>()
//val vaccinations = mutableStateOf<String>()
//val training = mutableStateOf<String>()
//// TODO: Need better category name for this
//val dietary = mutableStateOf<String>()

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
//                            viewModel.addRecord(record: )
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
fun RecordContent(navController: NavController, viewModel: RecordsViewModel) {
//    val medicalRecords = viewModel.records
//    if (medicalRecords.isEmpty()) {
//        EmptyRecordsContent("Medical Record") {
//            medicalRecords.add("New Medical Record")
//        }
//    } else {
//        LazyColumn(
//            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
//        ) {
//            items(medicalRecords) { record ->
//                ListItem(
//                    headlineContent = { Text(record) },
//                    supportingContent = { Text("Date: 2024-09-01") },
//                    trailingContent = {  },
//                    leadingContent = {
//                        Icon(
//                            Icons. Filled. Favorite,
//                            contentDescription = "Localized description",
//                            )
////                    headlineContent = { Text(record.title) },
////                    supportingContent = { Text("Date: ${record.date}") },
////                    modifier = Modifier.clickable {
////                        navController.navigate("medical_record_details/${record.title}")
//                    }
//                )
//                HorizontalDivider()
//            }
//        }
//    }
}

@Composable
fun VaccinationContent(navController: NavController) {
//    if (vaccinations.isEmpty()) {
//        EmptyContent("Vaccinations") {
//            vaccinations.add("New Vaccination")
//        }
//    } else {
//        LazyColumn(
//            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
//        ) {
//            items(vaccinations) { vaccine ->
//                ListItem(
//                    text = { Text(vaccine) },
//                    secondaryText = { Text("Vaccination Date: 2024-10-01") },
//                    modifier = Modifier.clickable {
//                        navController.navigate("vaccination_details/$vaccine")
//                    }
//                )
//                HorizontalDivider()
//            }
//        }
//    }
}


@Composable
fun TrainingContent(navController: NavController) {
//    if (dewormings.isEmpty()) {
//        EmptyContent("Dewormings") {
//            dewormings.add("New Deworming")
//        }
//    } else {
//        LazyColumn(
//            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
//        ) {
//            items(dewormings) { deworming ->
//                Text(text = deworming, modifier = Modifier.padding(8.dp))
//            }
//        }
//    }
}

@Composable
fun DietaryContent(navController: NavController) {
//    if (physicalExams.isEmpty()) {
//        EmptyContent("Physical Exams") {
//            physicalExams.add("New Physical Exam")
//        }
//    } else {
//        LazyColumn(
//            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
//        ) {
//            items(physicalExams) { exam ->
//                Text(text = exam, modifier = Modifier.padding(8.dp))
//            }
//        }
//    }
}

@Composable
fun EmptyRecordsContent(contentType: String, onAddClick: () -> Unit) {
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
}}
