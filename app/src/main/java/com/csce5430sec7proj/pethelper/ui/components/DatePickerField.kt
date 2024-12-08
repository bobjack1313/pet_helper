package com.csce5430sec7proj.pethelper.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.csce5430sec7proj.pethelper.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    modifier: Modifier = Modifier,
    selectedDate: Date?,
    onDateSelected: (Date) -> Unit // Callback for date selection
) {
    // State to manage the visibility of the calendar popup
    var showDatePicker by remember { mutableStateOf(false) }

    // State for managing the selected date
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate?.time
    )

    // State for displaying the date in the text field
    var dateText by remember { mutableStateOf(selectedDate?.let { convertMillisToDate(it.time) } ?: "") }

    val focusManager = LocalFocusManager.current

    Box(modifier = modifier.fillMaxWidth()) {
        // Text field for displaying and manually entering the date
        OutlinedTextField(
            value = dateText,
            onValueChange = { newText ->
                dateText = newText // Update the displayed text
            },
            label = { Text("Date of Birth") },
            trailingIcon = {
                IconButton(
                    onClick = {
                        focusManager.clearFocus() // Clear focus from the text field
                        val manualDate = parseDateWithMultipleFormats(dateText)
                        if (manualDate != null && isYearInValidRange(manualDate)) {
                            datePickerState.selectedDateMillis = manualDate.time
                        } else if (datePickerState.selectedDateMillis == null) {
                            // Default to current date if no date is selected
                            datePickerState.selectedDateMillis = System.currentTimeMillis()
                        }
                        showDatePicker = true // Open the calendar
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        // Show the DatePicker Popup if showDatePicker is true
        if (showDatePicker) {
            Popup(
                onDismissRequest = {
                    showDatePicker = false
                },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )

                    // Listen for date selection changes
                    LaunchedEffect(datePickerState.selectedDateMillis) {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedDate = Date(millis)
                            if (isYearInValidRange(selectedDate)) {
                                onDateSelected(selectedDate) // Callback for the selected date
                                dateText = convertMillisToDate(millis) // Update the displayed date
                            }
                        }
                    }
                }
            }
        }
    }
}

// Utility function to convert milliseconds to a formatted date string
fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

// Utility function to parse a date string with multiple formats
fun parseDateWithMultipleFormats(dateString: String): Date? {
    val formats = listOf(
        "MM/dd/yyyy",  // Full year format (e.g., 10/21/2024)
        "MM/dd/yy",    // Two-digit year format (e.g., 10/21/24)
        "yyyy-MM-dd",  // ISO-like format (e.g., 2024-10-21)
        "MMM dd, yyyy", // Long month format (e.g., Oct 21, 2024)
        "dd/MM/yyyy"   // Day-first format (e.g., 21/10/2024)
    )

    for (format in formats) {
        try {
            val formatter = SimpleDateFormat(format, Locale.getDefault())
            formatter.isLenient = false // Strict parsing

            val parsedDate = formatter.parse(dateString) ?: continue

            // Ensure year is within the valid range
            val calendar = Calendar.getInstance().apply { time = parsedDate }
            val year = calendar.get(Calendar.YEAR)

            if (year in 1900..2100) {
                return parsedDate
            }
        } catch (e: Exception) {
            // Ignore and try the next format
        }
    }
    return null // Return null if no formats match
}

// Utility function to validate the year range
fun isYearInValidRange(date: Date): Boolean {
    val calendar = Calendar.getInstance()
    calendar.time = date
    val year = calendar.get(Calendar.YEAR)
    return year in 1900..2100
}