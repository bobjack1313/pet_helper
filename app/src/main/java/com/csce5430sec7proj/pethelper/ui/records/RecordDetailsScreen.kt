@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.csce5430sec7proj.pethelper.ui.records

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.csce5430sec7proj.pethelper.data.daos.RecordDao
import com.csce5430sec7proj.pethelper.data.entities.Record
import com.csce5430sec7proj.pethelper.data.entities.RecordType
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.firstOrNull
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RecordDetailScreen(
    navController: NavController,
    recordId: Int,
    recordType: RecordType,  // 添加 RecordType 参数
    recordDao: RecordDao,
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
        val record = recordDao.getRecord(recordId).firstOrNull()
        if (record != null) {
            descriptionState.value = record.description ?: ""
            dateState.value = record.date?.let { dateFormat.format(it) } ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${recordType.name.replace("_", " ")} Details") },  // 动态标题
                actions = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            val record = Record(
                                id = recordId,
                                petIdFk = 0,
                                type = recordType,  // 使用传入的 recordType
                                description = descriptionState.value,
                                date = null,
                                vendorIdFk = 0,
                                cost = 0.0
                            )
                            recordDao.delete(record)
                            navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Record")
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
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            // 日期选择输入框
            TextField(
                value = dateState.value,
                onValueChange = { dateState.value = it },
                label = { Text("Date") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // 打开日期选择器
                        DatePickerDialog(
                            context, { _, year, month, dayOfMonth ->
                                calendar.set(year, month, dayOfMonth)
                                dateState.value = dateFormat.format(calendar.time)
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    },
                readOnly = true  // 禁止手动输入，只能通过选择器设置
            )

            // 保存按钮
            Button(
                onClick = {
                    coroutineScope.launch {
                        val parsedDate = dateFormat.parse(dateState.value)
                        val updatedRecord = Record(
                            id = recordId,
                            petIdFk = 0,
                            type = recordType,  // 使用传入的 recordType
                            description = descriptionState.value,
                            date = parsedDate,
                            vendorIdFk = 0,
                            cost = 0.0
                        )
                        recordDao.update(updatedRecord)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save")
            }
        }
    }
}
