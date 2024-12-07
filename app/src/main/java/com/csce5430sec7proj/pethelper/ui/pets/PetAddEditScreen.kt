package com.csce5430sec7proj.pethelper.ui.pets


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
import com.csce5430sec7proj.pethelper.data.entities.Pet
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
import com.csce5430sec7proj.pethelper.data.entities.PetGender
import com.csce5430sec7proj.pethelper.data.entities.PetType
import java.text.SimpleDateFormat
import java.util.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.csce5430sec7proj.pethelper.utils.saveImageToStorage


@Composable
fun PetAddEditScreen(
    petId: Int?,
    onNavigateBack: () -> Unit,
) {
    val viewModel: PetsViewModel = viewModel()
    val petState = viewModel.state.collectAsState().value
    val pet: Pet? = petState.pets.find { it.id == petId }
    // Determine if the user prefers the metric system
    val isMetric: Boolean = remember {
        when (Locale.getDefault().country) {
            // Countries using imperial units
            "US", "LR", "MM" -> false
            // Default to metric
            else -> true
        }
    }
    val context = LocalContext.current
    val txtImgSelCan = stringResource(id = R.string.image_selection_cancelled)
    val txtUnit =   if (isMetric) stringResource(id = R.string.weight_kg)
                    else (stringResource(id = R.string.weight_lbs))
    // Mutable state for the selected pet
    val selectedPetState = remember(petId) {
        mutableStateOf(pet ?: Pet(
            0, "", PetType.OTHER,
        ))
    }

    // Fetch existing pet data if petId is not null
    LaunchedEffect(petId, pet) {
        if (petId != null && pet != null) {
            selectedPetState.value = pet
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                val savedImagePath = saveImageToStorage(context, uri)
                if (savedImagePath != null) {
                    selectedPetState.value = selectedPetState.value.copy(imagePath = savedImagePath)
                }
            } else {
                // Use the context safely
                Toast.makeText(context, txtImgSelCan, Toast.LENGTH_SHORT).show()
            }
        }
    )

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
                    selectedPetState.value = selectedPetState.value.copy(dateOfBirth = selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
        // Image Upload Section
        Box(
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .clickable { imagePickerLauncher.launch("image/*") }
        ) {
            val petImagePath = selectedPetState.value.imagePath

            if (!petImagePath.isNullOrEmpty()) {
//            if (selectedImageUri != null) {
                // Display the selected image
                Image(
                    painter = rememberAsyncImagePainter(model = petImagePath),
                    contentDescription = stringResource(id = R.string.selected_pet_image),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Placeholder for pet image
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = stringResource(id = R.string.add_pet_image),
                    modifier = Modifier.align(Alignment.Center).size(100.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                // Name Field
                TextField(
                    value = selectedPetState.value.name,
                    onValueChange = {
                        selectedPetState.value = selectedPetState.value.copy(name = it)
                    },
                    label = { Text(stringResource(id = R.string.name_hint)) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                // Pet Type Dropdown
//                EnumDropDownMenu(
//                    selectedValue = selectedPetState.value.type,
//                    label = "Pet Type",
//                    options = PetType.entries.toTypedArray(),
//                    onSelectionChange = {
//                        selectedPetState.value = selectedPetState.value.copy(type = it)
//                    }
//                )
                EnumDropDownMenu(
                    selectedValue = selectedPetState.value.type, // Get the current type from the selected pet state
                    label = stringResource(id = R.string.type_hint),
                    options = PetType.entries.toTypedArray(),
                    onSelectionChange = { selectedType ->
                        // Update the selected pet state with the new pet type
                        selectedPetState.value = selectedPetState.value.copy(type = selectedType)
                    }
                )

                // Breed Field
                TextField(
                    value = selectedPetState.value.breed ?: "",
                    onValueChange = {
                        selectedPetState.value = selectedPetState.value.copy(breed = it)
                    },
                    label = { Text(stringResource(id = R.string.breed_hint)) },
                    modifier = Modifier.fillMaxWidth()
                )

                // Breed Field
//        selectedPetState.value.breed?.let {
//            TextField(
//                value = it,
//                onValueChange = { selectedPetState.value = selectedPetState.value.copy(breed = it) },
//                label = { Text("Breed") },
//                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
//            )
//        }

                // Color Field
                selectedPetState.value.color?.let {
                    TextField(
                        value = it,
                        onValueChange = {
                            selectedPetState.value = selectedPetState.value.copy(color = it)
                        },
                        label = { Text(stringResource(id = R.string.color_hint)) },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )
                }

                // Pet Gender Dropdown
                EnumDropDownMenu(
                    selectedValue = selectedPetState.value.gender ?: PetGender.OTHER,
                    label = stringResource(id = R.string.gender_hint),
                    options = PetGender.entries.toTypedArray(),
                    onSelectionChange = {
                        selectedPetState.value = selectedPetState.value.copy(gender = it)
                    }
                )

                // Date of Birth Field
                TextField(
                    value = selectedPetState.value.dateOfBirth?.let {
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
                    } ?: "", // Provide an empty string if dateOfBirth is null
                    onValueChange = { /* No-op, since we're using a DatePicker */ },
                    label = { Text(stringResource(id = R.string.date_of_birth_hint)) },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { showDatePicker() }
                )

                // Weight Field with kg/lbs toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextField(
                        value = selectedPetState.value.weight.toString() + txtUnit,
                        onValueChange = {
                            selectedPetState.value =
                                selectedPetState.value.copy(weight = it.toDoubleOrNull() ?: 0.0)
                        },
                        label = { Text(stringResource(id = R.string.weight_hint)) },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Sterilized Toggle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text(stringResource(id = R.string.neutered_spayed))
                    Switch(
                        checked = selectedPetState.value.sterilized ?: false,
                        onCheckedChange = {
                            selectedPetState.value = selectedPetState.value.copy(sterilized = it)
                        }
                    )
                }

                // Allergies
                TextField(
                    value = selectedPetState.value.allergies?.joinToString(", ") ?: "",
                    onValueChange = {
                        selectedPetState.value =
                            selectedPetState.value.copy(allergies = it.split(",").map { it.trim() })
                    },
                    label = { Text(stringResource(id = R.string.allergies_hint)) },
                    modifier = Modifier.fillMaxWidth()
                )

                // Diet Field
                TextField(
                    value = selectedPetState.value.diet ?: "",
                    onValueChange = {
                        selectedPetState.value = selectedPetState.value.copy(diet = it)
                    },
                    label = { Text(stringResource(id = R.string.diet_hint)) },
                    modifier = Modifier.fillMaxWidth()
                )

                // Training Field
                TextField(
                    value = selectedPetState.value.training ?: "",
                    onValueChange = {
                        selectedPetState.value = selectedPetState.value.copy(training = it)
                    },
                    label = { Text(stringResource(id = R.string.training_hint)) },
                    modifier = Modifier.fillMaxWidth()
                )

                // Titles
                TextField(
                    value = selectedPetState.value.titles?.joinToString(", ") ?: "",
                    onValueChange = {
                        selectedPetState.value =
                            selectedPetState.value.copy(titles = it.split(",").map { it.trim() })
                    },
                    label = { Text(stringResource(id = R.string.titles_hint)) },
                    modifier = Modifier.fillMaxWidth()
                )

                // Notes
                TextField(
                    value = selectedPetState.value.notes ?: "",
                    onValueChange = {
                        selectedPetState.value = selectedPetState.value.copy(notes = it)
                    },
                    label = { Text(stringResource(id = R.string.notes_hint)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (petId == null) {
                            viewModel.addPet(selectedPetState.value)
                        } else {
                            viewModel.updatePet(selectedPetState.value)
                        }
                        onNavigateBack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (petId == null) stringResource(id = R.string.add_pet) else stringResource(
                            id = R.string.update_pet
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
        }}
    }
}