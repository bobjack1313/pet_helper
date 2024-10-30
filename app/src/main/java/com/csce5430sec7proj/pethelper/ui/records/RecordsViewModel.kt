package com.csce5430sec7proj.pethelper.ui.records

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csce5430sec7proj.pethelper.Graph
import com.csce5430sec7proj.pethelper.data.PetHelperRepository
import com.csce5430sec7proj.pethelper.data.entities.Record
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar
import java.util.Date

//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.viewModelScope
////import com.csce5430sec7proj.pethelper.data.daos.MedicalRecordDao
//import kotlinx.coroutines.launch


class RecordsViewModel(
    private val repository: PetHelperRepository = Graph.repository
) : ViewModel() {

    // Change MutableState to StateFlow
    private val _state: MutableStateFlow<RecordsState> = MutableStateFlow(RecordsState())
    val state: StateFlow<RecordsState> get() = _state

    var selectedTabIndex: MutableState<Int> = mutableStateOf(0)
        private set

    init {
        loadRecords()
    }

    private fun loadRecords() {
        viewModelScope.launch {
            when (selectedTabIndex.value) {
                0 -> {
                    repository.getRecords.collectLatest { recordsList ->
                        _state.value = state.value.copy(medicalRecords = recordsList)
                    }
                }
                // Add logic for loading other tab data if needed
            }
        }
    }

    fun showAddDialog() {
        _state.value = state.value.copy(isAddDialogVisible = true)
    }

    fun hideAddDialog() {
        _state.value = state.value.copy(isAddDialogVisible = false)
    }

    fun addRecord(record: Record) {
        viewModelScope.launch {
            repository.insertRecord(record)
            loadRecords() // Reload records to update the UI
        }
    }

    fun deleteRecord(record: Record) {
        viewModelScope.launch {
            repository.deleteRecord(record)
            loadRecords() // Reload records to update the UI
        }
    }

    fun updateRecord(record: Record) {
        viewModelScope.launch {
            repository.updateRecord(record)
            loadRecords() // Reload records to update the UI
        }
    }

    fun setSelectedTab(index: Int) {
        selectedTabIndex.value = index
        loadRecords()  // Reload data based on the selected tab
    }
}


data class RecordsState(
    val medicalRecords: List<Record> = emptyList(),
    val isAddDialogVisible: Boolean = false
)