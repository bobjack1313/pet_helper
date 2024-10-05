package com.csce5430sec7proj.pethelper.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csce5430sec7proj.pethelper.ui.Pet

class PetSharedViewModel : ViewModel() {
    private val _petList = MutableLiveData<List<Pet>>()
    val petList: LiveData<List<Pet>> get() = _petList

    init {
        _petList.value = listOf()
    }

    fun addPet(pet: Pet) {
        val updatedList = _petList.value.orEmpty().toMutableList()
        updatedList.add(pet)
        _petList.value = updatedList
    }
}
