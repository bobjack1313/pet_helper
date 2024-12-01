package com.csce5430sec7proj.pethelper.ui.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csce5430sec7proj.pethelper.data.entities.VetContact
import com.csce5430sec7proj.pethelper.data.daos.VetContactDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// 定义用于管理 UI 状态的数据类
data class VetContactsState(
    val contacts: List<VetContact> = emptyList()
)

class VetContactViewModel(
    private val dao: VetContactDao
) : ViewModel() {

    // 使用 StateFlow 管理 UI 状态
    private val _state: MutableStateFlow<VetContactsState> = MutableStateFlow(VetContactsState())
    val state: StateFlow<VetContactsState> get() = _state

    init {
        loadContacts()
    }

    // 加载所有联系信息
    private fun loadContacts() {
        viewModelScope.launch {
            val contacts = dao.getAllContacts()
            _state.update { it.copy(contacts = contacts) }
        }
    }

    // 保存新的联系信息
    fun saveContact(phoneNumber: String, emailAddress: String, message: String) {
        val vetContact = VetContact(phoneNumber = phoneNumber, emailAddress = emailAddress, message = message)
        viewModelScope.launch {
            dao.insert(vetContact)
            loadContacts() // 插入后重新加载数据更新 UI
        }
    }

    fun getAllContacts(): StateFlow<VetContactsState> = state
}
