@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.csce5430sec7proj.pethelper.ui.records

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import com.csce5430sec7proj.pethelper.data.entities.Record
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.ui.res.stringResource
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.ui.components.SortDropdown


@Composable
fun RecordsScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit,
    onNavigateDetail: (Int) -> Unit,
) {
    val recordsViewModel: RecordsViewModel = viewModel()
    val recordsState by recordsViewModel.state.collectAsState()
    val records = recordsViewModel.getFilteredRecords()
    val sortOptions = listOf("All", "Pet", "Type", "Service")
    var selectedSortOption by remember { mutableStateOf(sortOptions[0]) }
    var sortDescending by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.pet_records)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                actions = {
                    // Sorting Dropdown
                    SortDropdown(
                        options = sortOptions,
                        selectedOption = selectedSortOption,
                        onOptionSelected = {
                            selectedSortOption = it
                            recordsViewModel.setSortOption(it)
                        }
                    )
                    // Toggle for sorting by date
                    IconButton(onClick = { sortDescending = !sortDescending }) {
                        Icon(
                            imageVector = if (sortDescending) Icons.Default.KeyboardArrowDown
                                            else Icons.Default.KeyboardArrowUp,
                            contentDescription = stringResource(id = R.string.sort_order_toggle),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (records.isNotEmpty()) {
                FloatingActionButton(onClick = { onNavigate(-1) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add_record),
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (records.isEmpty()) {
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { onNavigate(-1) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.add_record),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(records.sortedWith(recordsViewModel.getSortComparator(sortDescending))) { record ->
                        RecordRow(
                            record = record,
                            onDelete = { recordsViewModel.deleteRecord(record) },
                            onNavigateDetail = {
                                onNavigate(record.id)
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun RecordRow(
    record: Record,
    onDelete: () -> Unit,
    onNavigateDetail: () -> Unit
) {
    ListItem(
        headlineContent = {
            Text(
                record.description ?: stringResource(id = R.string.no_description),
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        supportingContent = {
            Text(
                text = record.date?.let {
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it)
                } ?: stringResource(id = R.string.no_date),
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingContent = {
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete_record),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = Modifier
            .clickable { onNavigateDetail() }
            .background(MaterialTheme.colorScheme.surface)
    )
    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
}