package com.csce5430sec7proj.pethelper.ui.records

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.csce5430sec7proj.pethelper.data.entities.Record
import java.text.SimpleDateFormat
import java.util.*
import com.csce5430sec7proj.pethelper.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun RecordDetailScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit,
    recordId: Int?,
    onNavigateBack: () -> Boolean,
) {
    val recordViewModel: RecordsViewModel = viewModel()
    val recordState = recordViewModel.state.collectAsState().value
    val record: Record? = recordState.records.find { it.id == recordId }

    // State variables to handle confirmations
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val descriptionState = remember { mutableStateOf("") }
    val dateState = remember { mutableStateOf("") }


    // 日期格式化工具
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val calendar = Calendar.getInstance()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    record?.let { onNavigate(it.id) }
                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(id = R.string.update_record)
                )
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
            // Description input field
            OutlinedTextField(
                value = descriptionState.value,
                onValueChange = { descriptionState.value = it },
                label = { Text(text = stringResource(id = R.string.description_hint)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                ),
                textStyle = MaterialTheme.typography.bodyLarge
            )

            // Date selection button
            Button(
                onClick = {
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.select_date),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Display selected date
            Text(
                text = stringResource(
                    id = R.string.selected_date,
                    dateState.value
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Delete record button
            Button(
                onClick = {
                    record?.let {
                        // Show the confirmation dialog
                        showDeleteConfirmation = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text(
                    text = stringResource(id = R.string.delete),
                    style = MaterialTheme.typography.bodyLarge
                )
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
                    Text(
                        text = stringResource(id = R.string.confirm_delete_title),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                text = {
                    Text(
                        text = stringResource(id = R.string.confirm_delete_message_record),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
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
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.delete),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            // Dismiss the dialog without deleting
                            showDeleteConfirmation = false
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                containerColor = MaterialTheme.colorScheme.surface,
            )
        }
    }
}
