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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.data.entities.ServiceCategory


@Composable
fun ServicesScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit,
    onNavigateDetail: (Int) -> Unit,
) {
    val viewModel: ServicesViewModel = viewModel()
    val servicesState by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1976D2)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (servicesState.services.isEmpty()) {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { onNavigate(-1) }) {
                    Text(text = stringResource(id = R.string.add_service))
                }
            }
        } else {
            Scaffold(
                modifier = modifier,
                floatingActionButton = {
                    FloatingActionButton(onClick = { onNavigate(-1) }) {
                        Icon(imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.add_service))
                    }
                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    items(servicesState.services) { service ->
                        ServiceRow(
                            serviceName = service.name, onClick = {
                                onNavigateDetail(service.id)
                            },
                            serviceCategory = service.category
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
    onClick: () -> Unit
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
            .height(120.dp)
            .padding(8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Display the service's icon
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                // Optional: Background color for the icon
                .background(Color.Gray)
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
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 35.sp),
            color = Color.Black
        )
    }
}