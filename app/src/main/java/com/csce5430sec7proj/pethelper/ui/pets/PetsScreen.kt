package com.csce5430sec7proj.pethelper.ui.pets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.data.entities.Pet
import java.sql.Date


@Composable
fun PetsScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit,
    onNavigateInLine: (String) -> Unit
) {
    // Using the view model
    val petsViewModel: PetsViewModel = viewModel()
    val petsState = petsViewModel.state.value

    // Temporary mock data for testing
    val mockPets = remember {
        mutableStateOf(
            listOf(
                Pet(
                    id = 1,
                    name = "Buddy",
                    breed = "Golden Retriever",
                    color = "Golden",
                    age = 3,
                    dateOfBirth = Date(0),  // Placeholder date (Unix epoch time)
                    aggression = 0.1
                ),
                Pet(
                    id = 2,
                    name = "Mittens",
                    color = "white",
                    age = 12,
                    dateOfBirth = Date(0),
                    breed = "Cat",
                    aggression = 0.0
                ),
                Pet(
                    id = 3,
                    name = "Charlie",
                    color = "white",
                    age = 12,
                    dateOfBirth = Date(0),
                    breed = "Fish",
                    aggression = 0.0
                )
            )
        )
    }

    // If the pets list is empty, show a centered "Add Pet" button
    if (petsState.pets.isEmpty()) {
    // Check if pets list is empty and show "Add Pet" button in center
//    if (mockPets.value.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = { onNavigateInLine("pet_edit_screen") }) {
                Text("Add Pet")
            }
        }
    } else {
        // Otherwise, display the list of pets
        Scaffold(
            modifier = modifier,
            floatingActionButton = {
                FloatingActionButton(onClick = { onNavigateInLine("pet_edit_screen") }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Ask pet"
                    )
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Use mock data for now to test the layout
                items(mockPets.value) { pet ->
                    PetRow(petName = pet.name, onClick = {
                        // Navigate to PetDetailScreen with the pet's ID
                        onNavigate(pet.id)
                    })
                    HorizontalDivider()
                }
            }
        }
    }
}


@Composable
fun PetRow(
    petName: String,
    onClick: () -> Unit // Pass an onClick handler as a parameter
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Placeholder for pet image (replace with real image later)
        Image(
            // Placeholder image
            painter = painterResource(id = R.drawable.pet_placeholder),
            contentDescription = "Pet Picture",
            modifier = Modifier
                // Set the size for the pet image
                .size(100.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))

        // Pet name text
        Text(
            text = petName,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 35.sp),
            color = Color.Black
        )
    }
}
