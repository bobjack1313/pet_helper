package com.csce5430sec7proj.pethelper.ui.services

import com.csce5430sec7proj.pethelper.ui.pets.EnumDropDownMenu
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.csce5430sec7proj.pethelper.data.entities.Service
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.data.entities.ServiceCategory
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceAddEditScreen(
    serviceId: Int?,
    onNavigateBack: () -> Unit,
) {
    val viewModel: ServicesViewModel = viewModel()
    val serviceState = viewModel.state.collectAsState().value
    val service: Service? = serviceState.services.find { it.id == serviceId }
    // Determine if the user prefers the metric system
    val context = LocalContext.current

    // Mutable state for the selected service
    val selectedServiceState = remember(serviceId) {
        mutableStateOf(service ?: Service(
            0, "", "", "", "", ServiceCategory.OTHER,
            "", "", "",
        ))
    }

    // Fetch existing service data if serviceId is not null
    LaunchedEffect(serviceId, service) {
        if (serviceId != null && service != null) {
            selectedServiceState.value = service
        }
    }

    Scaffold(
        topBar = {
            androidx.compose.material3.CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (serviceId == null) stringResource(id = R.string.add_service)
                                else stringResource(id = R.string.update_service),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Add-Edit Layout
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(0.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    item {
                        Text( text = stringResource(id = R.string.select_service_type) )
                        EnumDropDownMenu(
                            selectedValue = selectedServiceState.value.category,
                            label = stringResource(id = R.string.type_hint),
                            options = ServiceCategory.entries.toTypedArray(),
                            onSelectionChange = { selectedCategory ->
                                // Update the selected service state with the new service type
                                selectedServiceState.value = selectedServiceState.value.copy(
                                    category = selectedCategory)
                            }
                        )

                        // Name Field
                        TextField(
                            value = selectedServiceState.value.name,
                            onValueChange = {
                                selectedServiceState.value = selectedServiceState.
                                                                value.copy(name = it)
                            },
                            label = { Text(stringResource(id = R.string.name_hint)) },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                        )

                        // Description Field
                        TextField(
                            value = selectedServiceState.value.description ?: "",
                            onValueChange = {
                                selectedServiceState.value = selectedServiceState.value.
                                                                            copy(description = it)
                            },
                            label = { Text(stringResource(id = R.string.description_hint)) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Address Field
                        TextField(
                            value = selectedServiceState.value.address ?: "",
                            onValueChange = {
                                selectedServiceState.value = selectedServiceState.value.
                                                                            copy(address = it)
                            },
                            label = { Text(stringResource(id = R.string.address_hint)) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Phone Field
                        TextField(
                            value = selectedServiceState.value.phone ?: "",
                            onValueChange = {
                                selectedServiceState.value = selectedServiceState.value.
                                                                                copy(phone = it)
                            },
                            label = { Text(stringResource(id = R.string.phone_hint)) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Secondary Phone Field
                        TextField(
                            value = selectedServiceState.value.secondaryPhone ?: "",
                            onValueChange = {
                                selectedServiceState.value = selectedServiceState.value.
                                                                        copy(secondaryPhone = it)
                            },
                            label = { Text(stringResource(id = R.string.secondary_phone_hint)) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Email Field
                        TextField(
                            value = selectedServiceState.value.email ?: "",
                            onValueChange = {
                                selectedServiceState.value = selectedServiceState.value.
                                copy(email = it)
                            },
                            label = { Text(stringResource(id = R.string.email_hint)) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Notes
                        TextField(
                            value = selectedServiceState.value.notes ?: "",
                            onValueChange = {
                                selectedServiceState.value = selectedServiceState.value.copy(notes = it)
                            },
                            label = { Text(stringResource(id = R.string.notes_hint)) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                if (serviceId == null) {
                                    viewModel.addService(selectedServiceState.value)
                                } else {
                                    viewModel.updateService(selectedServiceState.value)
                                }
                                onNavigateBack()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (serviceId == null) stringResource(id = R.string.add_service)
                                        else stringResource(id = R.string.update_service)
                            )
                        }
                    }
                }
            }
        }
    }
}