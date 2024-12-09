@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.csce5430sec7proj.pethelper.ui.records

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.csce5430sec7proj.pethelper.data.entities.Record
import com.csce5430sec7proj.pethelper.data.entities.RecordType
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import com.csce5430sec7proj.pethelper.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.csce5430sec7proj.pethelper.data.entities.Pet
import com.csce5430sec7proj.pethelper.ui.pets.PetsViewModel


@Composable
fun RecordDetailScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit,
    recordId: Int?,
    onNavigateBack: () -> Boolean,
) {
    val recordViewModel: RecordsViewModel = viewModel()
    val descriptionState = remember { mutableStateOf("") }
    val dateState = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val recordState = recordViewModel.state.collectAsState().value
    val record: Record? = recordState.records.find { it.id == recordId }

    // 日期格式化工具
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val calendar = Calendar.getInstance()

    // State variables to handle confirmations
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    // 使用 LaunchedEffect 加载单个记录
//    LaunchedEffect(recordId) {
//        val record = recordViewModel.getRecordById(recordId)
//        if (record != null) {
//            descriptionState.value = record.description ?: ""
//            dateState.value = record.date?.let { dateFormat.format(it) } ?: ""
//        }
//    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                record?.let { onNavigate(it.id) }
            }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = stringResource(id = R.string.Edit))
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 描述输入框
            TextField(
                value = descriptionState.value,
                onValueChange = { descriptionState.value = it },
                label = { Text(stringResource(id = R.string.description_hint)) },
                modifier = Modifier.fillMaxWidth()
            )

            // 日期选择按钮
            Button(onClick = {
                DatePickerDialog(
                    context, { _, year, month, dayOfMonth ->
                        calendar.set(year, month, dayOfMonth)
                        dateState.value = dateFormat.format(calendar.time)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }) {
                Text(stringResource(id = R.string.select_date))
            }

            // 显示选择的日期
            Text(
                text = stringResource(
                    id = R.string.selected_date,
                    dateState.value ?: stringResource(id = R.string.unknown)
                )
            )

            // Deleting a record
            Button(
                onClick = {
                    record?.let {
                        // Show the confirmation dialog
                        showDeleteConfirmation = true
                    }
                },
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(text = stringResource(id = R.string.delete))
            }
        }

        // Delete Confirmation Dialog
        if (showDeleteConfirmation) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog without taking action
                    showDeleteConfirmation = false
                },
                title = {
                    Text(text = stringResource(id = R.string.confirm_delete_title))
                },
                text = {
                    Text(text = stringResource(id = R.string.confirm_delete_message_pet))
                },
                confirmButton = {
                    Button(
                        onClick = {
                            record?.let {
                                // Perform the delete action
                                recordViewModel.deleteRecord(it)
                                onNavigateBack()
                            }
                            // Dismiss the dialog
                            showDeleteConfirmation = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(text = stringResource(id = R.string.delete))
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            // Dismiss the dialog without deleting
                            showDeleteConfirmation = false
                        }
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                }
            )
        }
    }
}
