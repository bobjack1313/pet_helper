package com.csce5430sec7proj.pethelper.ui.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable // Importing clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotificationsScreen(modifier: Modifier = Modifier) {
    var notifications by remember { mutableStateOf(listOf<Notification>()) }
    var showAddNotificationDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1976D2)),  // Blue background
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Notifications",
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (notifications.isEmpty()) {
            // Placeholder for "No notifications found"
            Text(
                text = "No notifications found",
                fontSize = 20.sp,
                color = Color.White.copy(alpha = 0.7f)  // Light text color
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                items(notifications) { notification ->
                    NotificationItem(notification)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Add Notification button
        Button(
            onClick = { showAddNotificationDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text("Add Notification", fontSize = 16.sp)
        }
    }

    // Dialog for adding a new notification
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

@Composable
fun NotificationItem(notification: Notification) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = notification.categoryColor)  // Use category color
    ) {
        Text(
            text = notification.details,
            modifier = Modifier.padding(16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun AddNotificationDialog(onDismiss: () -> Unit, onConfirm: (Notification) -> Unit) {
    var notificationText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Vaccination Reminders") } // Default category

    val categories = listOf(
        NotificationCategory("Vaccination Reminders", Color.Red),
        NotificationCategory("Medication Reminders", Color.Blue),
        NotificationCategory("Vet Appointments", Color.Green),
        NotificationCategory("General Health Checkups", Color.Yellow)
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Add Notification", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        },
        text = {
            Column {
                Text("Enter the notification details:")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = notificationText,
                    onValueChange = { notificationText = it },
                    label = { Text("Notification") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Dropdown to select category
                Text("Select Category:")
                categories.forEach { category ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable { selectedCategory = category.name } // Use clickable here
                            .background(if (selectedCategory == category.name) Color.Gray else Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = category.name,
                            color = Color.White,
                            fontWeight = if (selectedCategory == category.name) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (notificationText.isNotBlank()) {
                    val categoryColor = categories.first { it.name == selectedCategory }.color
                    onConfirm(Notification(notificationText, selectedCategory, categoryColor))
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

// Notification Data Class
data class Notification(
    val details: String,
    val category: String,
    val categoryColor: Color // Store color for the category
)

// Category Data Class
data class NotificationCategory(
    val name: String,
    val color: Color
)
