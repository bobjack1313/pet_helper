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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.csce5430sec7proj.pethelper.R
import coil.compose.rememberAsyncImagePainter
import com.csce5430sec7proj.pethelper.data.entities.PetType
import com.csce5430sec7proj.pethelper.ui.components.PetTypeImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetsScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit,
    onNavigateDetail: (Int) -> Unit,
) {
    val viewModel: PetsViewModel = viewModel()
    val petsState by viewModel.state.collectAsState()
    val isShowingArchived by viewModel.isShowingArchived.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.tab_bar_pets),
                        style = MaterialTheme.typography.titleLarge
                    ) },

                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isShowingArchived) {
                                stringResource(id = R.string.active)
                            } else {
                                stringResource(id = R.string.archived)
                            },
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(end = 4.dp)
                        )

                        IconButton(
                            onClick = { viewModel.toggleShowArchived() },
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Icon(
                                imageVector = if (isShowingArchived)
                                    Icons.AutoMirrored.Filled.ArrowBack
                                else Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = if (isShowingArchived) {
                                    stringResource(id = R.string.show_active_pets)
                                } else {
                                    stringResource(id = R.string.show_archived_pets)
                                }
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (petsState.pets.isNotEmpty() && !isShowingArchived) {
                FloatingActionButton(onClick = { onNavigate(-1) },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add_pet),
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (petsState.pets.isEmpty() && !isShowingArchived) {
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { onNavigate(-1) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.add_pet),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(0.dp)
                ) {   items(petsState.pets) { pet ->
                    PetRow(
                        petName = pet.name,
                        petImagePath = pet.imagePath,
                        onClick = {
                            onNavigateDetail(pet.id)
                        },
                        petType = pet.type,
                    )
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.secondary,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 0.dp)
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
    onClick: () -> Unit,
    petType: PetType,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left side content: Pet image and name
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // Display the pet's image or a placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
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
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        // Right side: Pet type icon
        PetTypeImage(petType = petType, tint = MaterialTheme.colorScheme.secondaryContainer)
    }
}