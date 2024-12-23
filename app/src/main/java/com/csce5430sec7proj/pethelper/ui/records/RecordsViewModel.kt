package com.csce5430sec7proj.pethelper.ui.records

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csce5430sec7proj.pethelper.data.PetHelperRepository
import com.csce5430sec7proj.pethelper.data.entities.Record
import com.csce5430sec7proj.pethelper.data.entities.RecordType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update
import com.csce5430sec7proj.pethelper.Graph
import com.csce5430sec7proj.pethelper.data.entities.Pet
import com.csce5430sec7proj.pethelper.data.entities.Service
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn


// 定义用于管理 UI 状态的数据类
data class RecordsState(
    val records: List<Record> = emptyList()
)

class RecordsViewModel(
    private val repository: PetHelperRepository = Graph.repository
) : ViewModel() {

    // 使用 StateFlow 管理 UI 状态
    private val _state: MutableStateFlow<RecordsState> = MutableStateFlow(RecordsState())
    val state: StateFlow<RecordsState> get() = _state
    private val _selectedSortOption = MutableStateFlow("Type")
    val selectedSortOption: StateFlow<String> get() = _selectedSortOption

    // Expose active pets and services as flows for the UI
    val activePets: StateFlow<List<Pet>> = repository.getPets.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    val availableServices: StateFlow<List<Service>> = repository.getServices.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )
    var selectedTabIndex: MutableState<Int> = mutableStateOf(0)
        private set

    init {
        loadRecords()
    }

    // 一次性加载所有记录，并在 ViewModel 中按类型进行过滤
    private fun loadRecords() {
        viewModelScope.launch {
            repository.getRecords.collectLatest { records ->
                _state.update { it.copy(records = records) }
            }
        }
    }

    // 根据 selectedTabIndex 返回当前选中的记录类型
//    fun getFilteredRecords(): List<Record> {
//        return when (selectedTabIndex.value) {
//            0 -> _state.value.records.filter { it.type == RecordType.MEDICAL }
//            1 -> _state.value.records.filter { it.type == RecordType.GROOMING }
//            2 -> _state.value.records.filter { it.type == RecordType.VACCINATION }
//            3 -> _state.value.records.filter { it.type == RecordType.TRAINING }
//            4 -> _state.value.records.filter { it.type == RecordType.DIET }
//            else -> _state.value.records
//        }
//    }

    // 显示添加对话框
//    fun showAddDialog() {
//        _state.update { it.copy(isAddDialogVisible = true) }
//    }
//
//    // 隐藏添加对话框
//    fun hideAddDialog() {
//        _state.update { it.copy(isAddDialogVisible = false) }
//    }

    // 添加记录并更新列表
    fun addRecord(record: Record) {
        viewModelScope.launch {
            repository.insertRecord(record)
            loadRecords() // 插入后重新加载数据更新 UI
        }
    }

    // 删除记录并更新列表
    fun deleteRecord(record: Record) {
        viewModelScope.launch {
            repository.deleteRecord(record)
            loadRecords() // 删除后重新加载数据更新 UI
        }
    }

    // 更新记录并刷新 UI
    fun updateRecord(record: Record) {
        viewModelScope.launch {
            repository.updateRecord(record)
            loadRecords() // 更新后重新加载数据
        }
    }

    // 设置选中的标签，并加载相应的记录
    fun setSelectedTab(index: Int) {
        selectedTabIndex.value = index
    }
    suspend fun getRecordById(id: Int): Record? {
        return repository.getRecord(id).firstOrNull()
    }


    fun setSortOption(option: String) {
        _selectedSortOption.value = option
    }

    fun getFilteredRecords(): List<Record> {
        val sortOption = _selectedSortOption.value
        return when (sortOption) {
            "Type" -> _state.value.records.sortedBy { it.type.name }
            "Vendor" -> _state.value.records.sortedBy { it.serviceIdFk }
            "Pet" -> _state.value.records.sortedBy { it.petIdFk }
            else -> _state.value.records
        }
    }

    fun getSortComparator(descending: Boolean): Comparator<Record> {
        val comparator = when (_selectedSortOption.value) {
            "Type" -> compareBy<Record> { it.type.name }
            "Vendor" -> compareBy { it.serviceIdFk }
            "Pet" -> compareBy { it.petIdFk }
            else -> compareBy { it.date }
        }
        return if (descending) comparator.reversed() else comparator
    }
}
