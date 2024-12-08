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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.data.entities.Service
import com.csce5430sec7proj.pethelper.data.entities.ServiceCategory


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

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                service?.let { onNavigate(it.id) }
            }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = stringResource(id = R.string.Edit))
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
                    .background(Color.Gray)
                    .padding(padding)
            ) {
                val iconResId = when (service?.category)  {
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
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp),
                    color = Color.Black,
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
                Text( text = stringResource(id = R.string.description_hint) )
                Text(
                    text = service.description ?: "",
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 22.sp),
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Service Address
                Text( text = stringResource(id = R.string.address_hint) )
                Text(
                    text = service.address ?: "",
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 22.sp),
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Service Phone
                Text( text = stringResource(id = R.string.phone_hint) )
                Text(
                    text = service.phone ?: "",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Service Secondary Phone
                Text( text = stringResource(id = R.string.secondary_phone_hint) )
                Text(
                    text = service.secondaryPhone ?: "",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Service Email
                Text( text = stringResource(id = R.string.email_hint) )
                Text(
                    text = service.email ?: "",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Service Notes
                Text( text = stringResource(id = R.string.notes_hint) )
                Text(
                    text = service.notes ?: "",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                    color = Color.Black
                )
            } ?: run {
                Text(
                    text = stringResource(id = R.string.loading_service_details),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        service?.let {
                            viewModel.deleteService(it)
                            onNavigateBack()
                        }
                    },
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(text = stringResource(id = R.string.delete))
                }
            }
            }
        }
    }
}