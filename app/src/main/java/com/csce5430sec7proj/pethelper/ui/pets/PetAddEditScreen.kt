package com.csce5430sec7proj.pethelper.ui.pets


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.data.entities.PetType
import java.util.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.csce5430sec7proj.pethelper.data.entities.PetGender
import com.csce5430sec7proj.pethelper.ui.components.DatePickerField
import com.csce5430sec7proj.pethelper.ui.components.EnumDropDownMenu
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
    // Mutable state for the selected pet
    val selectedPetState = remember(petId) {
        mutableStateOf(
            pet ?: Pet(
                0, "", PetType.OTHER,
            )
        )
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image Upload Section
        item {
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable { imagePickerLauncher.launch("image/*") }
            ) {
                val petImagePath = selectedPetState.value.imagePath

                if (!petImagePath.isNullOrEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(model = petImagePath),
                        contentDescription = stringResource(id = R.string.selected_pet_image),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = stringResource(id = R.string.add_pet_image),
                        modifier = Modifier.align(Alignment.Center).size(100.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Name Field
        item {
            TextField(
                value = selectedPetState.value.name,
                onValueChange = {
                    selectedPetState.value = selectedPetState.value.copy(name = it)
                },
                label = {
                    Text(
                        stringResource(id = R.string.name_hint),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Pet Type Dropdown
        item {
            Text(
                text = stringResource(id = R.string.type_hint),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 0.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    EnumDropDownMenu(
                        selectedValue = selectedPetState.value.type,
                        label = stringResource(id = R.string.type_hint),
                        options = PetType.entries.toTypedArray(),
                        onSelectionChange = { selectedType ->
                            selectedPetState.value =
                                selectedPetState.value.copy(type = selectedType)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Breed Field
        item {
            TextField(
                value = selectedPetState.value.breed ?: "",
                onValueChange = {
                    selectedPetState.value = selectedPetState.value.copy(breed = it)
                },
                label = {
                    Text(
                        stringResource(id = R.string.breed_hint),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Gender Dropdown
        item {
            Text(
                text = stringResource(id = R.string.gender_hint),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 0.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    EnumDropDownMenu(
                        selectedValue = selectedPetState.value.gender ?: PetGender.OTHER,
                        label = stringResource(id = R.string.gender_hint),
                        options = PetGender.entries.toTypedArray(),
                        onSelectionChange = {
                                selectedPetState.value = selectedPetState.value.copy(gender = it)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        // Date of Birth Field
        item {
            DatePickerField(
                selectedDate = selectedPetState.value.dateOfBirth,
                onDateSelected = { selectedDate ->
                    selectedPetState.value =
                        selectedPetState.value.copy(dateOfBirth = selectedDate)
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        // Color Field
        item {
            TextField(
                value = selectedPetState.value.color ?: "",
                onValueChange = { selectedPetState.value = selectedPetState.value.copy(color = it) },
                label = { Text(stringResource(id = R.string.color_hint)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Weight Field
        item {
            TextField(
                value = selectedPetState.value.weight?.toString() ?: "",
                onValueChange = {
                    selectedPetState.value =
                        selectedPetState.value.copy(weight = it.toDoubleOrNull())
                },
                label = { Text(stringResource(id = R.string.weight_hint)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Microchip ID
        item {
            TextField(
                value = selectedPetState.value.microchipId ?: "",
                onValueChange = { selectedPetState.value = selectedPetState.value.copy(microchipId = it) },
                label = { Text(stringResource(id = R.string.microchip_number_hint)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Microchip Issuer
        item {
            TextField(
                value = selectedPetState.value.microchipIssuer ?: "",
                onValueChange = { selectedPetState.value = selectedPetState.value.copy(microchipIssuer = it) },
                label = { Text(stringResource(id = R.string.microchip_issuer_hint)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Aggression Level
        item {
            TextField(
                value = selectedPetState.value.aggression?.toString() ?: "",
                onValueChange = {
                    selectedPetState.value =
                        selectedPetState.value.copy(aggression = it.toDoubleOrNull())
                },
                label = { Text(stringResource(id = R.string.aggression_level)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Sterilized Toggle
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.neutered_spayed),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = selectedPetState.value.sterilized ?: false,
                    onCheckedChange = {
                        selectedPetState.value = selectedPetState.value.copy(sterilized = it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        }

        // Allergies
        item {
            TextField(
                value = selectedPetState.value.allergies?.joinToString(", ") ?: "",
                onValueChange = {
                    selectedPetState.value =
                        selectedPetState.value.copy(allergies = it.split(",").map { it.trim() })
                },
                label = { Text(stringResource(id = R.string.allergies_hint)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Diet
        item {
            TextField(
                value = selectedPetState.value.diet ?: "",
                onValueChange = { selectedPetState.value = selectedPetState.value.copy(diet = it) },
                label = { Text(stringResource(id = R.string.diet_hint)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Notes
        item {
            TextField(
                value = selectedPetState.value.notes ?: "",
                onValueChange = { selectedPetState.value = selectedPetState.value.copy(notes = it) },
                label = { Text(stringResource(id = R.string.notes_hint)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Save Button
        item {
            Button(
                onClick = {
                    if (petId == null) {
                        viewModel.addPet(selectedPetState.value)
                    } else {
                        viewModel.updatePet(selectedPetState.value)
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
                    text = if (petId == null) stringResource(id = R.string.add_pet) else stringResource(
                        id = R.string.update_pet
                    ),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
