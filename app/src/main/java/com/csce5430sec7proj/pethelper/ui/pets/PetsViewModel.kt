package com.csce5430sec7proj.pethelper.ui.pets

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csce5430sec7proj.pethelper.Graph
import com.csce5430sec7proj.pethelper.data.PetRepository
import com.csce5430sec7proj.pethelper.data.entities.Pet
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class PetsViewModel(
    private val repository: PetRepository = Graph.repository
) : ViewModel() {
    // Change MutableState to StateFlow
    private val _state: MutableStateFlow<PetsState> = MutableStateFlow(PetsState())
    val state: StateFlow<PetsState> get() = _state
    var selectedPet: MutableState<Pet?> = mutableStateOf(null)
        private set

    init {
        getPets()
    }

    private fun getPets() {
        viewModelScope.launch {
            repository.getPets.collectLatest { petsList ->
                _state.value = state.value.copy(pets = petsList)
            }
        }
    }

    // Updated getPet function to set the selected pet
    fun getPet(id: Int) {
        viewModelScope.launch {
            repository.getPet(id).collect { pet ->
                _state.value = _state.value.copy(selectedPet = pet)
            }
        }
    }

    fun deletePet(pet: Pet) {
        viewModelScope.launch {
            repository.deletePet(pet)
        }
    }

    fun addPet(pet: Pet) {
        viewModelScope.launch {
            repository.insertPet(pet)
        }
    }

    fun updatePet(pet: Pet) {
        viewModelScope.launch {
            repository.updatePet(pet)
        }
    }

    fun onPetCheckedChange(pet: Pet, isChecked: Boolean) {
        viewModelScope.launch {
            repository.updatePet(
                pet = pet.copy()
            )
        }
    }

    // A function to filter by pet ID and retrieve a single pet
    fun filterBy(petId: Int) {
        if (petId != 1000001) {
            viewModelScope.launch {
                repository.getPet(petId).collect { pet ->
                    // Update the selected pet state
                    selectedPet.value = pet
                }
            }
        } else {
            getPets()
        }
    }
}


data class PetsState(
    val pets: List<Pet> = emptyList(),
    val selectedPet: Pet? = null
)