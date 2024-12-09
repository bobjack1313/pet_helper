package com.csce5430sec7proj.pethelper.ui.services

import com.csce5430sec7proj.pethelper.ui.components.EnumDropDownMenu
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.data.entities.ServiceCategory
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun ServiceAddEditScreen(
    serviceId: Int?,
    onNavigateBack: () -> Unit,
) {
    val viewModel: ServicesViewModel = viewModel()
    val serviceState = viewModel.state.collectAsState().value
    val service: Service? = serviceState.services.find { it.id == serviceId }

    // Mutable state for the selected service
    val selectedServiceState = remember(serviceId) {
        mutableStateOf(
            service ?: Service(
                0, "", "", "", "", ServiceCategory.OTHER,
                "", "", "",
            )
        )
    }

    // Fetch existing service data if serviceId is not null
    LaunchedEffect(serviceId, service) {
        if (serviceId != null && service != null) {
            selectedServiceState.value = service
        }
    }
    // Add-Edit Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                // Service Type Dropdown
                Text(
                    text = stringResource(id = R.string.select_service_type),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                EnumDropDownMenu(
                    selectedValue = selectedServiceState.value.category,
                    label = stringResource(id = R.string.type_hint),
                    options = ServiceCategory.entries.toTypedArray(),
                    onSelectionChange = { selectedCategory ->
                        // Update the selected service state with the new service type
                        selectedServiceState.value = selectedServiceState.value.copy(
                            category = selectedCategory
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                )

                // Name Field
                TextField(
                    value = selectedServiceState.value.name,
                    onValueChange = {
                        selectedServiceState.value = selectedServiceState.value.copy(name = it)
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                // Description Field
                TextField(
                    value = selectedServiceState.value.description ?: "",
                    onValueChange = {
                        selectedServiceState.value =
                            selectedServiceState.value.copy(description = it)
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.description_hint),
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

                // Address Field
                TextField(
                    value = selectedServiceState.value.address ?: "",
                    onValueChange = {
                        selectedServiceState.value = selectedServiceState.value.copy(address = it)
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.address_hint),
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

                // Phone Field
                TextField(
                    value = selectedServiceState.value.phone ?: "",
                    onValueChange = {
                        selectedServiceState.value = selectedServiceState.value.copy(phone = it)
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.phone_hint),
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

                // Secondary Phone Field
                TextField(
                    value = selectedServiceState.value.secondaryPhone ?: "",
                    onValueChange = {
                        selectedServiceState.value =
                            selectedServiceState.value.copy(secondaryPhone = it)
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.secondary_phone_hint),
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

                // Email Field
                TextField(
                    value = selectedServiceState.value.email ?: "",
                    onValueChange = {
                        selectedServiceState.value = selectedServiceState.value.copy(email = it)
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.email_hint),
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

                // Notes Field
                TextField(
                    value = selectedServiceState.value.notes ?: "",
                    onValueChange = {
                        selectedServiceState.value = selectedServiceState.value.copy(notes = it)
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.notes_hint),
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

                Spacer(modifier = Modifier.height(16.dp))

                // Save Button
                Button(
                    onClick = {
                        if (serviceId == null) {
                            viewModel.addService(selectedServiceState.value)
                        } else {
                            viewModel.updateService(selectedServiceState.value)
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
                        text = if (serviceId == null) stringResource(id = R.string.add_service)
                        else stringResource(id = R.string.update_service),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}