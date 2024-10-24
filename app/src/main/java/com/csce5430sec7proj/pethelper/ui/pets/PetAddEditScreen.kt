package com.csce5430sec7proj.pethelper.ui.pets


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.csce5430sec7proj.pethelper.data.entities.Pet
import java.util.Date
import android.app.DatePickerDialog
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun PetAddEditScreen(
    petId: Int?,
    onNavigateBack: () -> Unit,
) {
    val viewModel: PetsViewModel = viewModel()
    val petState = viewModel.state.collectAsState().value
    val pet: Pet? = petState.pets.find { it.id == petId }

    // Mutable state for the selected pet
    val selectedPetState = remember(petId) {
        mutableStateOf(pet ?: Pet(0, "", "", "", 0, Date(), 0.0))
    }
    val context = LocalContext.current

    // Fetch existing pet data if petId is not null
    LaunchedEffect(petId, pet) {
        if (petId != null && pet != null) {
            selectedPetState.value = pet
        }
    }

    // Add-Edit Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(0.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Function to open date picker
        fun showDatePicker() {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    // Update the date of birth state
                    val selectedDate = Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }.time
                    selectedPetState.value = selectedPetState.value.copy(dateOfBirth = selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
        // Image Upload Section
        Box(
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .padding(8.dp)
                .clickable { /* TODO: Open image picker to select an image */ }
        ) {
            // Placeholder for pet image
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Add Pet Image",
                modifier = Modifier.align(Alignment.Center).size(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Name Field
        TextField(
            value = selectedPetState.value.name,
            onValueChange = { selectedPetState.value = selectedPetState.value.copy(name = it) },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        // Breed Field
        TextField(
            value = selectedPetState.value.breed,
            onValueChange = { selectedPetState.value = selectedPetState.value.copy(breed = it) },
            label = { Text("Breed") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        // Color Field
        TextField(
            value = selectedPetState.value.color,
            onValueChange = { selectedPetState.value = selectedPetState.value.copy(color = it) },
            label = { Text("Color") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        // Age Field
        TextField(
            value = selectedPetState.value.age.toString(),
            onValueChange = { selectedPetState.value = selectedPetState.value.copy(
                age = it.toIntOrNull() ?: 0) },
            label = { Text("Age") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        // Date of Birth Field
        TextField(
            value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedPetState.value.dateOfBirth),
            onValueChange = { /* No-op, since we're using a DatePicker */ },
            label = { Text("Date of Birth") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { showDatePicker() }
        )

//        TextField(
//           value = selectedPetState.value.weight.toString(),
//        onValueChange = { selectedPetState.value = selectedPetState.value.copy(
//            weight = it.toIntOrNull() ?: 0) },
//            label = { Text("Weight") },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
//        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (petId == null) {
                    viewModel.addPet(selectedPetState.value)
                } else {
                    viewModel.updatePet(selectedPetState.value)
                }
                onNavigateBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (petId == null) "Add Pet" else "Update Pet")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}