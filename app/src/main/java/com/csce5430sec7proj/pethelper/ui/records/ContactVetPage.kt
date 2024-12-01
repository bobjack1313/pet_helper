@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.csce5430sec7proj.pethelper.ui.records

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ContactVetPage(viewModel: VetContactViewModel = viewModel()) {
    var phoneNumber by remember { mutableStateOf(TextFieldValue()) }
    var emailAddress by remember { mutableStateOf(TextFieldValue()) }
    var message by remember { mutableStateOf(TextFieldValue()) }
    var validationMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contact Vet") }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            TextField(
                value = emailAddress,
                onValueChange = { emailAddress = it },
                label = { Text("Email Address") },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            TextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Message") },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Button(
                onClick = {
                    if (validateContactDetails(phoneNumber.text, emailAddress.text)) {
                        viewModel.saveContact(phoneNumber.text, emailAddress.text, message.text)
                        validationMessage = "Contact details saved successfully!"
                    } else {
                        validationMessage = "Invalid contact details. Please check and try again."
                    }
                },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Save Contact Details")
            }

            Text(validationMessage, style = MaterialTheme.typography.bodyLarge)
        }
    }}

fun validateContactDetails(phoneNumber: String, emailAddress: String): Boolean {
    val phonePattern = "^\\+?[0-9]{10,13}$".toRegex()
    val emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$".toRegex()
    return phonePattern.matches(phoneNumber) && emailPattern.matches(emailAddress)
}
