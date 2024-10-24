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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.data.entities.Pet

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

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                pet?.let { onNavigate(it.id) }
            }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Pet")
            }
        }
    ) { _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .padding(8.dp)
            ) {
                Image(
                    // Placeholder image
                    painter = painterResource(id = R.drawable.pet_placeholder),
                    contentDescription = "Pet Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            }

            // Pet Name
            pet?.let {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp),
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            // Details in two columns
            pet?.let {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(
                            text = "ID: ${it.id}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Breed: ${it.breed}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Color: ${it.color}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                            color = Color.Black
                        )
                    }

                    Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                        Text(
                            text = "Age: ${it.age} years",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Date of Birth: ${it.dateOfBirth}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // Text(
                        //     text = "Weight: ${it.weight} kg",
                        //     style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                        //     color = Color.Black
                        // )
                    }
                }
            } ?: run {
                // Handle the case where pet data is not yet available
                Text(
                    text = "Loading pet details...",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
