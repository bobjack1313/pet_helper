package com.csce5430sec7proj.pethelper.ui.records

import com.csce5430sec7proj.pethelper.ui.components.EnumDropDownMenu
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.csce5430sec7proj.pethelper.data.entities.Record
import com.csce5430sec7proj.pethelper.data.entities.RecordType
import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.csce5430sec7proj.pethelper.R
import java.util.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.csce5430sec7proj.pethelper.ui.components.DatePickerField


@Composable
fun RecordAddEditScreen(
    navController: NavController,
    recordId: Int?,
    onNavigateBack: () -> Unit,
) {
    val viewModel: RecordsViewModel = viewModel()
    val recordState = viewModel.state.collectAsState().value
    val activePets = viewModel.activePets.collectAsState(emptyList()).value
    val availableServices = viewModel.availableServices.collectAsState(emptyList()).value
    val record: Record? = recordState.records.find { it.id == recordId }
    val context = LocalContext.current

    val selectedRecordState = remember(recordId) {
        mutableStateOf(
            record ?: Record(
                id = 0,
                petIdFk = 0,
                type = RecordType.OTHER,
                description = "",
                date = null
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(0.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Row with two buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        // Implement PDF scanning functionality here
                        Toast.makeText(context, "Scanning PDF...", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.scan_PDF),
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Button(
                    onClick = {
                        navController.navigate("pdf_picker_screen")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.import_PDF),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    // Select Type
                    Text(
                        text = stringResource(id = R.string.record_type),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    EnumDropDownMenu(
                        selectedValue = selectedRecordState.value.type,
                        label = stringResource(id = R.string.type_hint),
                        options = RecordType.entries.toTypedArray(),
                        onSelectionChange = { selectedType ->
                            selectedRecordState.value = selectedRecordState.value.copy(
                                type = selectedType
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Description Field
                    TextField(
                        value = selectedRecordState.value.description ?: "",
                        onValueChange = {
                            selectedRecordState.value = selectedRecordState.value.copy(
                                description = it
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.description_hint),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedTextColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Date Select
                    Text(
                        text = stringResource(id = R.string.select_date),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    DatePickerField(
                        selectedDate = selectedRecordState.value.date,
                        onDateSelected = { date ->
                            selectedRecordState.value =
                                selectedRecordState.value.copy(date = date)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (recordId == null) {
                                viewModel.addRecord(selectedRecordState.value)
                            } else {
                                viewModel.updateRecord(selectedRecordState.value)
                            }
                            onNavigateBack()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (recordId == null) stringResource(id = R.string.add_record)
                            else stringResource(id = R.string.update_record),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
