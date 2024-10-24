package com.csce5430sec7proj.pethelper.ui.pets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.csce5430sec7proj.pethelper.R


@Composable
fun PetsScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit,
    onNavigateDetail: (Int) -> Unit,
) {
    val viewModel: PetsViewModel = viewModel()
    val petsState by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1976D2)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (petsState.pets.isEmpty()) {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { onNavigate(-1) }) {
                    Text("Add Pet")
                }
            }
        } else {
            Scaffold(
                modifier = modifier,
                floatingActionButton = {
                    FloatingActionButton(onClick = { onNavigate(-1) }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Pet")
                    }
                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    items(petsState.pets) { pet ->
                        PetRow(petName = pet.name, onClick = {
                            onNavigateDetail(pet.id)
                        })
                    }
                }
            }
        }
    }
}



@Composable
fun PetRow(
    petName: String,
    onClick: () -> Unit
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
