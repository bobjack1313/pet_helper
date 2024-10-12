package com.csce5430sec7proj.pethelper.ui.pets

import androidx.compose.runtime.MutableState
import com.csce5430sec7proj.pethelper.ui.Category
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csce5430sec7proj.pethelper.Graph
import com.csce5430sec7proj.pethelper.data.PetRepository
import com.csce5430sec7proj.pethelper.data.entities.Pet
import com.csce5430sec7proj.pethelper.ui.Utils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PetsViewModel(
    private val repository: PetRepository = Graph.repository
) : ViewModel() {
    var state: MutableState<PetsState> = mutableStateOf(PetsState())
        private set

    init {
        getPets()
    }

    private fun getPets() {
        viewModelScope.launch {
            repository.getPets.collectLatest { petsList ->
                // Correct usage of copy method on state.value
                state.value = state.value.copy(pets = petsList)
            }
        }
    }

    fun deletePet(pet: Pet) {
        viewModelScope.launch {
            repository.deletePet(pet)
        }
    }

    fun onCategoryChange(category: Category) {
        state.value = state.value.copy(selectedCategory = category)
        filterBy(category.id)
    }

    fun onPetCheckedChange(pet: Pet, isChecked: Boolean) {
        viewModelScope.launch {
            repository.updatePet(
                pet = pet.copy()
            )
        }
    }

    private fun filterBy(petId: Int) {
        if (petId != 1000001) {
            viewModelScope.launch {
                repository.getPet(petId).collectLatest { pet ->
                    // Correct usage of copy method on state.value
                    state.value = state.value.copy(pets = listOf(pet))
                }
            }
        } else {
            getPets()
        }
    }
}


data class PetsState(
    val pets: List<Pet> = emptyList(),
    val selectedCategory: Category = Category()
)