package com.csce5430sec7proj.pethelper.ui.pets

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize



// Works
@Composable
fun <T : Enum<T>> EnumDropDownMenu(
    selectedValue: T,
    label: String,
    options: Array<T>,
    onSelectionChange: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart)) {
        Text(
            text = selectedValue.name,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    expanded = true
                })
        )
//        OutlinedTextField(
//            value = selectedValue.name,
//            onValueChange = {},
//            label = { Text(label) },
//            readOnly = true,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
////                .onGloballyPositioned { coordinates ->
////                    textFieldSize = coordinates.size.toSize()
////                }
//                .clickable { expanded = true }
//        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onSelectionChange(option) // Pass the selected option back
                        expanded = false
                    },
                    text = { Text(option.name) }
                )
            }
        }
    }
}

//@Composable
//fun <T : Enum<T>> EnumDropDownMenu(
//    selectedValue: T,
//    label: String,
//    options: Array<T>,
//    onSelectionChange: (T) -> Unit
//) {
//    var expanded by remember { mutableStateOf(false) }
//    var textFieldSize by remember { mutableStateOf(Size.Zero) }
//
//    // Display the selected value as a text field
//    OutlinedTextField(
//        value = selectedValue.name,
//        onValueChange = {},
//        label = { Text(label) },
//        readOnly = true,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//            .onGloballyPositioned { coordinates ->
//                textFieldSize = coordinates.size.toSize() // Capture size of the OutlinedTextField
//            }
//            .clickable { expanded = true } // Set expanded to true on click
//    )
//
//    // Dropdown menu showing available options
//    DropdownMenu(
//        expanded = expanded,
//        onDismissRequest = { expanded = false },
//        modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() }) // Match dropdown width to OutlinedTextField
//    ) {
//        options.forEach { option ->
//            DropdownMenuItem(
//                onClick = {
//                    onSelectionChange(option) // Pass the selected option back
//                    expanded = false
//                },
//                text = { Text(option.name) }
//            )
//        }
//    }
//}

