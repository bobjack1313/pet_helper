package com.csce5430sec7proj.pethelper.ui.services

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
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.data.entities.Service
import com.csce5430sec7proj.pethelper.data.entities.ServiceCategory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.csce5430sec7proj.pethelper.ui.components.LabelWithText


@Composable
fun ServiceDetailsScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit,
    serviceId: Int?,
    onNavigateBack: () -> Boolean,
) {
    val viewModel: ServicesViewModel = viewModel()
    val serviceState = viewModel.state.collectAsState().value
    val service: Service? = serviceState.services.find { it.id == serviceId }
    // State variables to handle confirmations
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { service?.let { onNavigate(it.id) } },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(id = R.string.edit_service),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
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
            // Icon Placeholder Box
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(padding)
            ) {
                val iconResId = when (service?.category) {
                    ServiceCategory.GROOMER -> R.drawable.ic_bird
                    ServiceCategory.TRAINER -> R.drawable.ic_cat
                    ServiceCategory.DAYCARE -> R.drawable.ic_cow
                    ServiceCategory.PET_STORE -> R.drawable.ic_dog
                    ServiceCategory.BOARDING -> R.drawable.ic_lizard
                    ServiceCategory.VET -> R.drawable.ic_pet_abc
                    ServiceCategory.OTHER -> R.drawable.ic_fish
                    else -> R.drawable.ic_pet_placeholder
                }

                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = stringResource(id = R.string.service_picture),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Inside
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            service?.let {
                // Service Name
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Align remaining content to the left
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                service?.let {
                    // Service Description
                    LabelWithText(
                        label = stringResource(id = R.string.description_hint),
                        text = it.description ?: ""
                    )

                    // Service Address
                    LabelWithText(
                        label = stringResource(id = R.string.address_hint),
                        text = it.address ?: ""
                    )

                    // Service Phone
                    LabelWithText(
                        label = stringResource(id = R.string.phone_hint),
                        text = it.phone ?: ""
                    )

                    // Service Secondary Phone
                    LabelWithText(
                        label = stringResource(id = R.string.secondary_phone_hint),
                        text = it.secondaryPhone ?: ""
                    )

                    // Service Email
                    LabelWithText(
                        label = stringResource(id = R.string.email_hint),
                        text = it.email ?: ""
                    )

                    // Service Notes
                    LabelWithText(
                        label = stringResource(id = R.string.notes_hint),
                        text = it.notes ?: ""
                    )
                } ?: run {
                    Text(
                        text = stringResource(id = R.string.loading_service_details),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Delete a service
                Button(
                    onClick = {
                        service?.let {
                            // Show the confirmation dialog
                            showDeleteConfirmation = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text(text = stringResource(id = R.string.delete))
                }

                // Delete Confirmation Dialog
                if (showDeleteConfirmation) {
                    AlertDialog(
                        onDismissRequest = { showDeleteConfirmation = false },
                        title = {
                            Text(
                                text = stringResource(id = R.string.confirm_delete_title),
                                style = MaterialTheme.typography.titleLarge,
                              //  color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        text = {
                            Text(
                                text = stringResource(id = R.string.confirm_delete_message_service),
                                style = MaterialTheme.typography.bodyLarge,
                              //  color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    service?.let {
                                        viewModel.deleteService(it)
                                        onNavigateBack()
                                    }
                                    showDeleteConfirmation = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) {
                                Text(text = stringResource(id = R.string.delete))
                            }
                        },
                        dismissButton = {
                            Button(onClick = { showDeleteConfirmation = false }) {
                                Text(text = stringResource(id = R.string.cancel))
                            }
                        }
                    )
                }
            }
        }
    }
}