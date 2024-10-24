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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


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
                when (selectedTabIndex) {
                    0 -> medicalRecords.add("New Medical Record")
                    1 -> vaccinations.add("New Vaccination")
                    2 -> dewormings.add("New Deworming")
                    3 -> physicalExams.add("New Physical Exam")
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
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }
            // 根据选中的标签展示不同的内容
            when (selectedTabIndex) {
                0 -> MedicalRecordContent(navController)
                1 -> VaccinationContent(navController)
                2 -> DewormingContent(navController)
                3 -> PhysicalExamContent(navController)
            }
        }
    }
}

val medicalRecords = mutableStateListOf<String>()
val vaccinations = mutableStateListOf<String>()
val dewormings = mutableStateListOf<String>()
val physicalExams = mutableStateListOf<String>()

@Composable
fun MedicalRecordContent(navController: NavController) {
    if (medicalRecords.isEmpty()) {
        EmptyContent("Medical Record") {
            medicalRecords.add("New Medical Record")
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(medicalRecords) { record ->
                ListItem(
                    headlineContent = { Text(record) },
                    supportingContent = { Text("Date: 2024-09-01") },
                    trailingContent = {  },
                    leadingContent = {
                        Icon(
                            Icons. Filled. Favorite,
                            contentDescription = "Localized description",
                            )
                    }
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun VaccinationContent(navController: NavController) {
    if (vaccinations.isEmpty()) {
        EmptyContent("Vaccinations") {
            vaccinations.add("New Vaccination")
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(vaccinations) { vaccine ->
//                ListItem(
//                    text = { Text(vaccine) },
//                    secondaryText = { Text("Vaccination Date: 2024-10-01") },
//                    modifier = Modifier.clickable {
//                        navController.navigate("vaccination_details/$vaccine")
//                    }
//                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun DewormingContent(navController: NavController) {
    if (dewormings.isEmpty()) {
        EmptyContent("Dewormings") {
            dewormings.add("New Deworming")
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(dewormings) { deworming ->
                Text(text = deworming, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun PhysicalExamContent(navController: NavController) {
    if (physicalExams.isEmpty()) {
        EmptyContent("Physical Exams") {
            physicalExams.add("New Physical Exam")
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(physicalExams) { exam ->
                Text(text = exam, modifier = Modifier.padding(8.dp))
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
        Text(text = "$contentType is empty")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAddClick) {
            Text(text = "Add Record")
        }
    }
}
