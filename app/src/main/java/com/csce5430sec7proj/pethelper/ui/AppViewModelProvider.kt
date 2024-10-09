package com.csce5430sec7proj.pethelper.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.csce5430sec7proj.pethelper.PetHelperApplication

//import com.csce5430sec7proj.pethelper.InventoryApplication
//import com.csce5430sec7proj.pethelper.ui.home.HomeViewModel
//import com.csce5430sec7proj.pethelper.ui.pet.PetDetailsViewModel
//import com.csce5430sec7proj.pethelper.ui.pet.PetEditViewModel
//import com.csce5430sec7proj.pethelper.ui.pet.PetEntryViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
//    val Factory = viewModelFactory {
//        // Initializer for PetEditViewModel
//        initializer {
//            PetEditViewModel(
//                this.createSavedStateHandle()
//            )
//        }
//        // Initializer for PetEntryViewModel
//        initializer {
//            PetEntryViewModel(petHelperApplication().container.petsRepository)
//        }
//
//        // Initializer for PetDetailsViewModel
//        initializer {
//            PetDetailsViewModel(
//                this.createSavedStateHandle()
//            )
//        }
//
//        // Initializer for HomeViewModel
//        initializer {
////            HomeViewModel()
//        }
//    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [PetHelperApplication].
 */
fun CreationExtras.petHelperApplication(): PetHelperApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as PetHelperApplication)
