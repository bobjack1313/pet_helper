package com.csce5430sec7proj.pethelper.data

import com.csce5430sec7proj.pethelper.data.entities.AppointmentType
import com.csce5430sec7proj.pethelper.data.entities.VendorCategory

// Simple interface for models that have a category to be compared in the UI
interface CategoryType {
    val category: String
}

fun AppointmentType.toVendorCategory(): VendorCategory {
    return when (this) {
        AppointmentType.VET -> VendorCategory.VET
        AppointmentType.GROOMER -> VendorCategory.GROOMER
        AppointmentType.TRAINER -> VendorCategory.TRAINER
        AppointmentType.BOARDING -> VendorCategory.BOARDING
        AppointmentType.DAYCARE -> VendorCategory.DAYCARE
        AppointmentType.OTHER -> VendorCategory.OTHER
    }
}

fun VendorCategory.toAppointmentType(): AppointmentType {
    return when (this) {
        VendorCategory.VET -> AppointmentType.VET
        VendorCategory.GROOMER -> AppointmentType.GROOMER
        VendorCategory.TRAINER -> AppointmentType.TRAINER
        VendorCategory.BOARDING -> AppointmentType.BOARDING
        VendorCategory.DAYCARE -> AppointmentType.DAYCARE
        VendorCategory.PET_STORE -> AppointmentType.OTHER
        VendorCategory.OTHER -> AppointmentType.OTHER
    }
}