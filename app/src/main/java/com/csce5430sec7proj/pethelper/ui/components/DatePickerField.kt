package com.csce5430sec7proj.pethelper.ui.components

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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalFocusManager
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
                dateText = newText
            },
            label = {
                Text(
                    "Date of Birth",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        focusManager.clearFocus()
                        val manualDate = parseDateWithMultipleFormats(dateText)
                        if (manualDate != null && isYearInValidRange(manualDate)) {
                            datePickerState.selectedDateMillis = manualDate.time
                        } else if (datePickerState.selectedDateMillis == null) {
                            // Default to current date if no date is selected
                            datePickerState.selectedDateMillis = System.currentTimeMillis()
                        }
                        showDatePicker = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
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
                                onDateSelected(selectedDate)
                                dateText = convertMillisToDate(millis)
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
        "MM/dd/yyyy",
        "MM/dd/yy",
        "yyyy-MM-dd",
        "MMM dd, yyyy",
        "dd/MM/yyyy"
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
    return null
}

// Utility function to validate the year range
fun isYearInValidRange(date: Date): Boolean {
    val calendar = Calendar.getInstance()
    calendar.time = date
    val year = calendar.get(Calendar.YEAR)
    return year in 1900..2100
}