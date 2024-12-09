package com.csce5430sec7proj.pethelper.ui.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.csce5430sec7proj.pethelper.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(modifier: Modifier = Modifier) {
    var notifications by remember { mutableStateOf(listOf<Notification>()) }
    var showAddNotificationDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.tab_bar_notifications),
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
            if (notifications.isEmpty()) {
                FloatingActionButton(onClick = { showAddNotificationDialog = true },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add_notification),
                    )
                }
            }
        }
    ) { padding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (notifications.isEmpty()) {
                // Placeholder for "No notifications found"
                Text(
                    text = stringResource(id = R.string.no_notifications_found),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    items(notifications) { notification ->
                        NotificationItem(notification)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Add Notification button
            Button(
                onClick = { showAddNotificationDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.add_notification),
                    style = MaterialTheme.typography.bodyLarge
                )
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
}


// Notification Data Class
data class Notification(
    val details: String,
    val category: String,
    val categoryColor: Color
)

// Category Data Class
data class NotificationCategory(
    val name: String,
    val color: Color
)
