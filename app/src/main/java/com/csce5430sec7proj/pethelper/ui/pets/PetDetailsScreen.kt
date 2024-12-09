package com.csce5430sec7proj.pethelper.ui.pets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.data.entities.Pet
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue



@Composable
fun PetDetailsScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit,
    petId: Int?,
    onNavigateBack: () -> Boolean,
) {
    val viewModel: PetsViewModel = viewModel()
    val petState = viewModel.state.collectAsState().value
    val pet: Pet? = petState.pets.find { it.id == petId }
    val petWithAge = pet?.let { viewModel.getPetWithAge(it) }
    val isMetric: Boolean = remember {
        when (Locale.getDefault().country) {
            // Countries using imperial units
            "US", "LR", "MM" -> false
            // Default to metric
            else -> true
        }
    }
    // State variables to handle confirmations
    var showArchiveConfirmation by remember { mutableStateOf(false) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                pet?.let { onNavigate(it.id) }
                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = stringResource(id = R.string.Edit))
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image Placeholder Box
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .padding(padding)
            ) {
                if (!pet?.imagePath.isNullOrEmpty()) {
                    // Load and display the pet's image
                    Image(
                        painter = rememberAsyncImagePainter(model = pet?.imagePath),
                        contentDescription = stringResource(id = R.string.pet_picture),
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Fallback placeholder image
                    Image(
                        painter = painterResource(id = R.drawable.pet_placeholder),
                        contentDescription = stringResource(id = R.string.pet_picture_placeholder),
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            // Pet Name
            pet?.let {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                )
            }

            petWithAge?.let { (pet, age) ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {

                    // Pet Type
                    Text(
                        text = stringResource(id = R.string.pet_type, pet.type.toString()),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Pet Gender
                    Text(
                        text = stringResource(
                            id = R.string.pet_gender,
                            pet.gender ?: stringResource(id = R.string.unknown)
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Pet Breed
                    Text(
                        text = stringResource(
                            id = R.string.pet_breed,
                            pet.breed ?: stringResource(id = R.string.unknown)
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Pet Color
                    Text(
                        text = stringResource(
                            id = R.string.pet_color,
                            pet.color ?: stringResource(id = R.string.not_specified)
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Microchip ID
                    Text(
                        text = stringResource(
                            id = R.string.pet_microchip_id,
                            pet.microchipId ?: stringResource(id = R.string.not_available)
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Pet Age
                    Text(
                        text = stringResource(
                            id = R.string.pet_age,
                            age?.toString() ?: stringResource(id = R.string.unknown)
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Date of Birth
                    Text(
                        text = stringResource(
                            id = R.string.pet_date_of_birth,
                            pet.dateOfBirth?.let {
                                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
                            } ?: stringResource(id = R.string.unknown)
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Pet Weight
                    Text(
                        text = stringResource(
                            id = if (isMetric) R.string.pet_weight_kg else R.string.pet_weight_lbs,
                            pet.weight?.toString() ?: stringResource(id = R.string.unknown)
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            } ?: run {
                Text(
                    text = stringResource(id = R.string.loading_pet_details),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Archiving a pet
                Button(
                    onClick = {
                        pet?.let {
                            // Show the confirmation dialog
                            showArchiveConfirmation = true
                        }
                    },
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                ) {
                    Text(
                        text = if (pet?.archived == true) {
                            stringResource(id = R.string.unarchive)
                        } else {
                            stringResource(id = R.string.archive)
                        }
                    )
                }

                // Archive Confirmation Dialog
                if (showArchiveConfirmation) {
                    AlertDialog(
                        onDismissRequest = {
                            // Dismiss the dialog without taking action
                            showArchiveConfirmation = false
                        },
                        title = {
                            Text(text = stringResource(id = R.string.confirm_archive_title))
                        },
                        text = {
                            Text(text = stringResource(id = R.string.confirm_archive_message_pet))
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    pet?.let {
                                        // Perform the archive action
                                        viewModel.updatePet(it.copy(archived = !it.archived))
                                    }
                                    // Dismiss the dialog
                                    showArchiveConfirmation = false
                                },
                            ) {
                                Text(text = stringResource(id = R.string.archive))
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    // Dismiss the dialog without archiving
                                    showArchiveConfirmation = false
                                }
                            ) {
                                Text(text = stringResource(id = R.string.cancel))
                            }
                        }
                    )
                }
                // Deleting a pet
                Button(
                    onClick = {
                        pet?.let {
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
                                pet?.let {
                                    // Perform the delete action
                                    viewModel.deletePet(it)
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
}