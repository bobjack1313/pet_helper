package com.csce5430sec7proj.pethelper.ui.services

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csce5430sec7proj.pethelper.Graph
import com.csce5430sec7proj.pethelper.data.PetHelperRepository
import com.csce5430sec7proj.pethelper.data.entities.Service
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class ServicesViewModel(
    private val repository: PetHelperRepository = Graph.repository
) : ViewModel() {
    // Change MutableState to StateFlow
    private val _state: MutableStateFlow<ServicesState> = MutableStateFlow(ServicesState())
    val state: StateFlow<ServicesState> get() = _state
    var selectedService: MutableState<Service?> = mutableStateOf(null)
        private set

    init {
        getServices()
    }

    private fun getServices() {
        viewModelScope.launch {
            repository.getServices.collectLatest { servicesList ->
                _state.value = state.value.copy(services = servicesList)
            }
        }
    }

    // Updated getService function to set the selected service
    fun getService(id: Int) {
        viewModelScope.launch {
            repository.getService(id).collect { service ->
                _state.value = _state.value.copy(selectedService = service)
            }
        }
    }

    fun deleteService(service: Service) {
        viewModelScope.launch {
            repository.deleteService(service)
        }
    }

    fun addService(service: Service) {
        viewModelScope.launch {
            repository.insertService(service)
        }
    }

    fun updateService(service: Service) {
        viewModelScope.launch {
            repository.updateService(service)
        }
    }

    fun onServiceCheckedChange(service: Service, isChecked: Boolean) {
        viewModelScope.launch {
            repository.updateService(
                service = service.copy()
            )
        }
    }

    // A function to filter by service ID and retrieve a single service
    fun filterBy(serviceId: Int) {
        if (serviceId != 1000001) {
            viewModelScope.launch {
                repository.getService(serviceId).collect { service ->
                    // Update the selected service state
                    selectedService.value = service
                }
            }
        } else {
            getServices()
        }
    }
}


data class ServicesState(
    val services: List<Service> = emptyList(),
    val selectedService: Service? = null
)