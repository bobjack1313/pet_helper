package com.csce5430sec7proj.pethelper.ui.records

import com.csce5430sec7proj.pethelper.ui.pets.EnumDropDownMenu
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.csce5430sec7proj.pethelper.data.entities.Record
import com.csce5430sec7proj.pethelper.data.entities.RecordType
import android.app.DatePickerDialog
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Switch
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.csce5430sec7proj.pethelper.R
import java.text.SimpleDateFormat
import java.util.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.csce5430sec7proj.pethelper.data.entities.Pet
import com.csce5430sec7proj.pethelper.utils.saveImageToStorage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordAddEditScreen(
    navController: NavController,
    recordId: Int?,
    onNavigateBack: () -> Unit,
) {
    val viewModel: RecordsViewModel = viewModel()
    val recordState = viewModel.state.collectAsState().value
    val record: Record? = recordState.records.find { it.id == recordId }
    val context = LocalContext.current

    val selectedRecordState = remember(recordId) {
        mutableStateOf(record ?: Record(
            0, 0, RecordType.OTHER, "", null,
        ))
    }
    // Add-Edit Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(0.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Function to open date picker
        fun showDatePicker() {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    // Update the date of birth state
                    val selectedDate = Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }.time
                    selectedRecordState.value = selectedRecordState.value.copy(date = selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

    // Fetch existing record data if recordId is not null
    LaunchedEffect(recordId, record) {
        if (recordId != null && record != null) {
            selectedRecordState.value = record
        }
    }
    Scaffold(
        topBar = {
            androidx.compose.material3.CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (recordId == null) stringResource(id = R.string.add_record)
                        else stringResource(id = R.string.update_record),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Row with two buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    // Implement PDF scanning functionality here
                    Toast.makeText(context, "Scanning PDF...", Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = stringResource(id = R.string.scan_PDF))
                }

                Button(onClick = {
                    navController.navigate("pdf_picker_screen")
                }) {
                    Text(text = stringResource(id = R.string.import_PDF))
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    // Select Type
                    Text("Record Type")
                    EnumDropDownMenu(
                        selectedValue = selectedRecordState.value.type,
                        label = stringResource(id = R.string.type_hint),
                        options = RecordType.entries.toTypedArray(),
                        onSelectionChange = { selectedType ->
                            // Update the selected record state with the new record type
                            selectedRecordState.value = selectedRecordState.value.copy(
                                type = selectedType)
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Description Field
                    selectedRecordState.value.description?.let { it ->
                        TextField(
                            value = it,
                            onValueChange = {
                                selectedRecordState.value = selectedRecordState.value.copy(
                                    description = it)
                            },
                            label = { Text(stringResource(id = R.string.description_hint)) },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    // Select pet
                    Text("Pet")
//                // Pet Select Dropdown
//                EnumDropDownMenu(
//                    selectedValue = selectedRecordState.value.petIdFk ?: null,
//                    label = stringResource(id = R.string.select_pet),
//                    options = Pets.entries.toTypedArray(),
//                    onSelectionChange = {
//                        selectedRecordState.value = selectedRecordState.value.copy(petIdFk = it)
//                    }
//                )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Date")
                    // Date Select
                    TextField(
                        value = selectedRecordState.value.date?.let {
                            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
                        } ?: "", // Provide an empty string if date is null
                        onValueChange = { /* No-op, since we're using a DatePicker */ },
                        label = { Text(stringResource(id = R.string.date_hint)) },
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { showDatePicker() }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Vendor")

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
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (recordId == null) stringResource(id = R.string.add_record) else stringResource(
                             id = R.string.update_record
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
}

//@Composable
//fun PetDropDownMenu(
//    pets: List<Pet>,
//    selectedPetId: Int?,
//    onPetSelected: (Int?) -> Unit
//) {
//    // Dropdown state
//    var expanded by remember { mutableStateOf(false) }
//    var selectedPetName by remember { mutableStateOf(pets.find { it.id == selectedPetId }?.name ?: "") }
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentSize(Alignment.TopStart)
//    ) {
//        // Display selected pet or placeholder
//        TextField(
//            value = selectedPetName,
//            onValueChange = { },
//            label = { Text(text = stringResource(id = R.string.select_pet)) },
//            readOnly = true,
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable { expanded = !expanded },
//            trailingIcon = {
//                Icon(
//                    imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
//                    contentDescription = null
//                )
//            }
//        )
//
//        // Dropdown menu
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            pets.forEach { pet ->
//                DropdownMenuItem(
//                    onClick = {
//                        selectedPetName = pet.name
//                        expanded = false
//                        onPetSelected(pet.id)
//                    }
//                ) {
//                    Text(text = pet.name)
//                }
//            }
//        }
//    }
//}