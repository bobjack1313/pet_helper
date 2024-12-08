@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.csce5430sec7proj.pethelper.ui.records

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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

@Composable
fun RecordDetailScreen(
    navController: NavController,
    recordId: Int,
    recordType: RecordType,
    recordsViewModel: RecordsViewModel,
    onNavigate: (String) -> Unit = {}
) {
    val descriptionState = remember { mutableStateOf("") }
    val dateState = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // 日期格式化工具
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val calendar = Calendar.getInstance()

    // 使用 LaunchedEffect 加载单个记录
    LaunchedEffect(recordId) {
        val record = recordsViewModel.getRecordById(recordId)
        if (record != null) {
            descriptionState.value = record.description ?: ""
            dateState.value = record.date?.let { dateFormat.format(it) } ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource( id = R.string.record_details_title,
                            recordType.name.replace("_", " "))) },
                actions = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            recordsViewModel.deleteRecord(Record(id = recordId, petIdFk = 0,
                                type = recordType, description = descriptionState.value,
                                date = null, serviceIdFk = 0, cost = 0.0))
                            navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = stringResource(
                            id = R.string.delete_record))
                    }
                }
            )
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

            // 保存按钮
            Button(
                onClick = {
                    if (descriptionState.value.isNotBlank()) {
                        coroutineScope.launch {
                            val parsedDate = try {
                                dateFormat.parse(dateState.value)
                            } catch (e: Exception) {
                                null
                            }
                            val updatedRecord = Record(
                                id = recordId,
                                petIdFk = 0,
                                type = recordType,
                                description = descriptionState.value,
                                date = parsedDate,
                                serviceIdFk = 0,
                                cost = 0.0
                            )
                            recordsViewModel.updateRecord(updatedRecord)
                            navController.popBackStack()
                        }
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(id = R.string.save))
            }
        }
    }
}
