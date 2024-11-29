package com.csce5430sec7proj.pethelper.ui.notifications

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import java.util.*

data class Notification(
    val details: String,
    val category: String,
    val categoryColor: Color,
    val date: String,
    val time: String,
    val id: Int = 0 // Adding an id to each notification for identification
)

@Composable
fun NotificationsScreen(modifier: Modifier = Modifier) {
    var notifications by remember { mutableStateOf<List<Notification>>(listOf()) }
    var showAddNotificationDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Text(
            text = "Notifications",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF424242),
            modifier = Modifier.padding(16.dp)
        )

        if (notifications.isEmpty()) {
            Text(
                text = "No notifications available.",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                items(notifications) { notification ->
                    NotificationItem(
                        notification,
                        onDelete = { notifications = notifications.filter { it.id != notification.id } },
                        onEdit = { showAddNotificationDialog = true } // open the dialog for editing
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { showAddNotificationDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text("Add Notification", fontSize = 16.sp)
        }

        if (showAddNotificationDialog) {
            AddNotificationDialog(
                onDismiss = { showAddNotificationDialog = false },
                onConfirm = { newNotification ->
                    notifications = notifications + newNotification
                    showAddNotificationDialog = false
                }
            )
        }
    }
}

@Composable
fun NotificationItem(
    notification: Notification,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = notification.categoryColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = notification.details,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF424242)
            )
            Text(
                text = "Category: ${notification.category}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "Date: ${notification.date}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "Time: ${notification.time}",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onEdit) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit")
                }

                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}

@Composable
fun AddNotificationDialog(onDismiss: () -> Unit, onConfirm: (Notification) -> Unit) {
    var notificationText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("General") } // Default to "General" category
    val categories = listOf(
        "Vaccination Reminders" to Color(0xFFBBDEFB),
        "Medication Reminders" to Color(0xFFC8E6C9),
        "Vet Appointments" to Color(0xFFFFF9C4),
        "General" to Color(0xFFD7CCC8)
    )

    var selectedDate by remember { mutableStateOf("Select Date") }
    var selectedTime by remember { mutableStateOf("Select Time") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            selectedTime = String.format("%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Notification") },
        text = {
            Column {
                OutlinedTextField(
                    value = notificationText,
                    onValueChange = { notificationText = it },
                    label = { Text("Notification Details") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Select Category:")
                categories.forEach { (category, _) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedCategory = category }
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = category,
                            fontWeight = if (selectedCategory == category) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Schedule Event:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Date Picker
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerDialog.show() }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Date: $selectedDate", color = Color.Black)
                }

                // Time Picker
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { timePickerDialog.show() }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Time: $selectedTime", color = Color.Black)
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (notificationText.isNotBlank() && selectedDate != "Select Date" && selectedTime != "Select Time") {
                    val categoryColor = categories.first { it.first == selectedCategory }.second
                    onConfirm(Notification(notificationText, selectedCategory, categoryColor, selectedDate, selectedTime))
                }
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
