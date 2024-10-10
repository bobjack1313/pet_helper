package com.csce5430sec7proj.pethelper.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csce5430sec7proj.pethelper.ui.Pet

class PetSharedViewModel : ViewModel() {

    // 将 _petList 改为 MutableList<Pet> 以便直接修改
    private val _petList = MutableLiveData<MutableList<Pet>>()
    val petList: LiveData<MutableList<Pet>> get() = _petList

    init {
        _petList.value = mutableListOf()
    }

    // 添加宠物
    fun addPet(pet: Pet) {
        val updatedList = _petList.value ?: mutableListOf()
        updatedList.add(pet)
        _petList.value = updatedList
    }

    // 更新宠物
    fun updatePet(currentPet: Pet, updatedPet: Pet) {
        val currentList = _petList.value ?: mutableListOf()
        val index = currentList.indexOf(currentPet)
        if (index != -1) {  // 确保找到要更新的宠物
            currentList[index] = updatedPet
            _petList.value = currentList // 通知 LiveData 更新
        }
    }

    // 删除宠物（可选）
    fun removePet(pet: Pet) {
        val currentList = _petList.value ?: mutableListOf()
        currentList.remove(pet)
        _petList.value = currentList // 通知 LiveData 更新
    }
}
