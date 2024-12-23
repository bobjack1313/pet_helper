package com.csce5430sec7proj.pethelper.ui.pets

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csce5430sec7proj.pethelper.Graph
import com.csce5430sec7proj.pethelper.data.PetHelperRepository
import com.csce5430sec7proj.pethelper.data.entities.Pet
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar
import java.util.Date


class PetsViewModel(
    private val repository: PetHelperRepository = Graph.repository
) : ViewModel() {
    // Change MutableState to StateFlow
    private val _state: MutableStateFlow<PetsState> = MutableStateFlow(PetsState())
    val state: StateFlow<PetsState> get() = _state
    var selectedPet: MutableState<Pet?> = mutableStateOf(null)
        private set
    private val _isShowingArchived = MutableStateFlow(false)
    val isShowingArchived: StateFlow<Boolean> get() = _isShowingArchived

    init {
        getPets()
    }

    private fun getPets() {
        viewModelScope.launch {
            _isShowingArchived.collectLatest { showArchived ->
                if (showArchived) {
                    repository.getArchivedPets().collectLatest { petsList ->
                        _state.value = state.value.copy(pets = petsList)
                    }
                } else {
                    repository.getPets.collectLatest { petsList ->
                        _state.value = state.value.copy(pets = petsList)
                    }
                }
            }
        }
    }

    fun getAllPets() {
                viewModelScope.launch {
                    repository.getAllPets.collectLatest { petsList ->
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


    fun getArchivedPets() {
        viewModelScope.launch {
            repository.getArchivedPets().collectLatest { archivedPetsList ->
                _state.value = state.value.copy(pets = archivedPetsList)
            }
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

    // Function to calculate age in years from dateOfBirth
    private fun calculateAge(dateOfBirth: Date?): Int? {
        if (dateOfBirth == null) return null

        val birthCalendar = Calendar.getInstance().apply { time = dateOfBirth }
        val currentCalendar = Calendar.getInstance()

        var age = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)
        if (currentCalendar.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        return age
    }

    // UI layer can access this computed property to display age for each pet
    fun getPetWithAge(pet: Pet): Pair<Pet, Int?> {
        if (pet.dateOfBirth != null) {
            val age = calculateAge(pet.dateOfBirth)
            return pet to age
        } else {
            return pet to null
        }
    }

    fun toggleShowArchived() {
        _isShowingArchived.value = !_isShowingArchived.value
    }
}


data class PetsState(
    val pets: List<Pet> = emptyList(),
    val selectedPet: Pet? = null
)