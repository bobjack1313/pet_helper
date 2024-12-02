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
import androidx.compose.ui.res.stringResource
import com.csce5430sec7proj.pethelper.R
import coil.compose.rememberAsyncImagePainter


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
                    Text(text = stringResource(id = R.string.add_pet))
                }
            }
        } else {
            Scaffold(
                modifier = modifier,
                floatingActionButton = {
                    FloatingActionButton(onClick = { onNavigate(-1) }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add_pet))
                    }
                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    items(petsState.pets) { pet ->
                        PetRow(
                            petName = pet.name, onClick = {
                                onNavigateDetail(pet.id)
                            },
                            petImagePath = pet.imagePath
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun PetRow(
    petName: String,
    petImagePath: String?,
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
        // Display the pet's image or a placeholder
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        ) {
            if (!petImagePath.isNullOrEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(model = petImagePath),
                    contentDescription = stringResource(id = R.string.pet_picture),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.pet_placeholder),
                    contentDescription = stringResource(id = R.string.pet_picture),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Pet name text
        Text(
            text = petName,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 35.sp),
            color = Color.Black
        )
    }
}