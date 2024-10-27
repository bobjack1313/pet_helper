package com.csce5430sec7proj.pethelper.ui.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.csce5430sec7proj.pethelper.data.daos.MedicalRecordDao

class RecordsViewModelFactory(
    private val medicalRecordDao: MedicalRecordDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecordsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecordsViewModel(medicalRecordDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
