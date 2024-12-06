package com.csce5430sec7proj.pethelper.ui.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csce5430sec7proj.pethelper.data.entities.VetContact
import com.csce5430sec7proj.pethelper.data.PetHelperRepository
import com.csce5430sec7proj.pethelper.Graph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

// 定义用于管理 UI 状态的数据类
data class VetContactsState(
    val contacts: List<VetContact> = emptyList(),
    val selectedContact: VetContact? = null
)

class VetContactViewModel(
    private val repository: PetHelperRepository = Graph.repository // 直接使用 PetHelperRepository
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
            repository.getAllVetContactsFlow().collectLatest { contacts ->
                _state.update { it.copy(contacts = contacts) }
            }
        }
    }

    // 保存新的联系信息
    fun saveContact(phoneNumber: String, emailAddress: String, message: String) {
        val vetContact = VetContact(phoneNumber = phoneNumber, emailAddress = emailAddress, message = message)
        viewModelScope.launch {
            repository.insertVetContact(vetContact)
            loadContacts() // 插入后重新加载数据更新 UI
        }
    }


    // 删除联系信息
    fun deleteContact(contact: VetContact) {
        viewModelScope.launch {
            repository.deleteVetContact(contact)
            loadContacts() // 删除后重新加载数据更新 UI
        }
    }
}
