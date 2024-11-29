package com.csce5430sec7proj.pethelper.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    // States for the settings
    var isNotificationEnabled by remember { mutableStateOf(true) }
    var isDarkThemeEnabled by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("English") }

    val languages = listOf("English", "Spanish", "French", "German", "Hindi")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Neutral light background
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title
        item {
            Text(
                text = "Settings",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF424242) // Neutral dark gray
            )
            Divider(color = Color(0xFFBDBDBD), thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
        }

        // Notifications Toggle
        item {
            SettingItem(
                title = "Enable Notifications",
                description = "Turn on/off app notifications",
                isChecked = isNotificationEnabled,
                onCheckedChange = { isNotificationEnabled = it }
            )
        }

        // Dark Theme Toggle
        item {
            SettingItem(
                title = "Dark Theme",
                description = "Switch between light and dark modes",
                isChecked = isDarkThemeEnabled,
                onCheckedChange = { isDarkThemeEnabled = it }
            )
        }

        // Language Preferences
        item {
            SettingDropdownItem(
                title = "Language",
                description = "Select your preferred language",
                options = languages,
                selectedOption = selectedLanguage,
                onOptionSelected = { selectedLanguage = it }
            )
        }

        // Account Section
        item {
            Text(
                text = "Account Settings",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF616161) // Neutral gray
            )
        }
        item {
            ButtonWithDescription(
                title = "Update Profile",
                description = "Edit your name, email, or phone number",
                onClick = { /* Navigate to Update Profile Screen */ }
            )
        }
        item {
            ButtonWithDescription(
                title = "Change Password",
                description = "Update your account password",
                onClick = { /* Navigate to Change Password Screen */ }
            )
        }
        item {
            ButtonWithDescription(
                title = "Delete Account",
                description = "Permanently delete your account",
                onClick = { /* Show delete confirmation dialog */ }
            )
        }

        // Privacy and Backup Section
        item {
            Text(
                text = "Privacy & Backup",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF616161) // Neutral gray
            )
        }
        item {
            ButtonWithDescription(
                title = "Privacy Policy",
                description = "Read our privacy policy",
                onClick = { /* Open Privacy Policy */ }
            )
        }
        item {
            ButtonWithDescription(
                title = "Clear App Cache",
                description = "Remove cached data to free up space",
                onClick = { /* Clear Cache Logic */ }
            )
        }
        item {
            ButtonWithDescription(
                title = "Backup Data",
                description = "Manually back up app data",
                onClick = { /* Trigger Data Backup */ }
            )
        }

        // About Section
        item {
            Text(
                text = "About",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF616161) // Neutral gray
            )
        }
        item {
            ButtonWithDescription(
                title = "App Version",
                description = "Version 1.0.0",
                onClick = { /* Show App Version Info */ }
            )
        }
        item {
            ButtonWithDescription(
                title = "Contact Support",
                description = "Get help or report a problem",
                onClick = { /* Navigate to Contact Support */ }
            )
        }
    }
}

@Composable
fun SettingItem(
    title: String,
    description: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF424242)
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color(0xFF757575)
            )
        }
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF1976D2),
                uncheckedThumbColor = Color.Gray
            )
        )
    }
}

@Composable
fun SettingDropdownItem(
    title: String,
    description: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF424242)
        )
        Text(
            text = description,
            fontSize = 14.sp,
            color = Color(0xFF757575)
        )
        Box {
            Button(
                onClick = { expanded = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F5)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = selectedOption, color = Color(0xFF424242))
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }, text = { Text(option) })
                }
            }
        }
    }
}

@Composable
fun ButtonWithDescription(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF424242)
        )
        Text(
            text = description,
            fontSize = 14.sp,
            color = Color(0xFF757575)
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
        ) {
            Text(text = "Manage", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen()
}
