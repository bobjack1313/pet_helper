package com.csce5430sec7proj.pethelper.ui.services

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.data.entities.ServiceCategory
import com.csce5430sec7proj.pethelper.ui.components.PetTypeImage
import com.csce5430sec7proj.pethelper.ui.pets.PetRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(
    modifier: Modifier = Modifier,
    onNavigateEdit: (Int) -> Unit,
    onNavigateContact: (Int) -> Unit,
    onNavigateDetail: (Int) -> Unit,
) {
    val viewModel: ServicesViewModel = viewModel()
    val servicesState by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.tab_bar_services),
                        style = MaterialTheme.typography.titleLarge
                    ) },

                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                actions = {}
            )
        },
        floatingActionButton = {
            if (servicesState.services.isNotEmpty()) {
                FloatingActionButton(onClick = { onNavigateEdit(-1) },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add_service),
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            if (servicesState.services.isEmpty()) {
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { onNavigateEdit(-1) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.add_service),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .padding(0.dp)
                ) {
                    items(servicesState.services) { service ->
                        ServiceRow(
                            serviceName = service.name,
                            onClick = { onNavigateDetail(service.id) },
                            serviceCategory = service.category
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
fun ServiceRow(
    serviceName: String,
    serviceCategory: ServiceCategory?,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    val iconResId = when (serviceCategory) {
        ServiceCategory.GROOMER -> R.drawable.ic_bird
        ServiceCategory.TRAINER -> R.drawable.ic_cat
        ServiceCategory.DAYCARE -> R.drawable.ic_cow
        ServiceCategory.PET_STORE -> R.drawable.ic_dog
        ServiceCategory.BOARDING -> R.drawable.ic_lizard
        ServiceCategory.VET -> R.drawable.ic_pet_abc
        ServiceCategory.OTHER -> R.drawable.ic_fish
        else -> R.drawable.ic_pet_placeholder
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 16.dp)
            .height(110.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Display the service's icon
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = stringResource(id = R.string.service_picture),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Inside
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        // Service name text
        Text(
            text = serviceName,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}