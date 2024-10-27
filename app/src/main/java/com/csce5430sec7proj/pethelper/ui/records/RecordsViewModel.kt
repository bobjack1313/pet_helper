package com.csce5430sec7proj.pethelper.ui.records

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.csce5430sec7proj.pethelper.data.daos.MedicalRecordDao
import com.csce5430sec7proj.pethelper.data.entities.MedicalRecord
import kotlinx.coroutines.launch

class RecordsViewModel(
    private val medicalRecordDao: MedicalRecordDao // 注入 DAO
) : ViewModel() {

    val selectedTabIndex = mutableStateOf(0)
    val medicalRecords = mutableStateListOf<MedicalRecord>()
    val vaccinations = mutableStateListOf<String>()
    val dewormings = mutableStateListOf<String>()
    val physicalExams = mutableStateListOf<String>()

    // 控制添加对话框的可见性
    val isAddDialogVisible = mutableStateOf(false)

    // 初始化时加载现有数据
    init {
        loadRecords()
    }

    // 从数据库加载记录
    private fun loadRecords() {
        viewModelScope.launch {
            when (selectedTabIndex.value) {
                0 -> {
                    val records = medicalRecordDao.getAllRecords()
                    medicalRecords.clear()
                    medicalRecords.addAll(records)
                }
                // 可以继续为其他选项卡加载数据
            }
        }
    }

    // 显示添加对话框
    fun showAddDialog() {
        isAddDialogVisible.value = true
    }

    // 隐藏添加对话框
    fun hideAddDialog() {
        isAddDialogVisible.value = false
    }

    // 添加记录到数据库
    fun addRecordToDatabase(title: String, date: String) {
        viewModelScope.launch {
            when (selectedTabIndex.value) {
                0 -> {
                    val newRecord = MedicalRecord(title = title, date = date)
                    medicalRecordDao.insert(newRecord)  // 插入数据库
                    medicalRecords.add(newRecord)       // 更新 UI
                }
                // 其他选项卡数据的插入逻辑可以在这里添加
            }
        }
    }

    // 更改选中的标签索引
    fun setSelectedTab(index: Int) {
        selectedTabIndex.value = index
        loadRecords()  // 重新加载选中的标签的数据
    }
}
