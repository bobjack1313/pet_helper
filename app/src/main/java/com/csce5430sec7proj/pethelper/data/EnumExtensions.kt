package com.csce5430sec7proj.pethelper.data

import com.csce5430sec7proj.pethelper.data.entities.AppointmentType
import com.csce5430sec7proj.pethelper.data.entities.ServiceCategory


// Simple interface for models that have a category to be compared in the UI
interface CategoryType {
    val category: String
}

fun AppointmentType.toServiceCategory(): ServiceCategory {
    return when (this) {
        AppointmentType.VET -> ServiceCategory.VET
        AppointmentType.GROOMER -> ServiceCategory.GROOMER
        AppointmentType.TRAINER -> ServiceCategory.TRAINER
        AppointmentType.BOARDING -> ServiceCategory.BOARDING
        AppointmentType.DAYCARE -> ServiceCategory.DAYCARE
        AppointmentType.OTHER -> ServiceCategory.OTHER
    }
}

fun ServiceCategory.toAppointmentType(): AppointmentType {
    return when (this) {
        ServiceCategory.VET -> AppointmentType.VET
        ServiceCategory.GROOMER -> AppointmentType.GROOMER
        ServiceCategory.TRAINER -> AppointmentType.TRAINER
        ServiceCategory.BOARDING -> AppointmentType.BOARDING
        ServiceCategory.DAYCARE -> AppointmentType.DAYCARE
        ServiceCategory.PET_STORE -> AppointmentType.OTHER
        ServiceCategory.OTHER -> AppointmentType.OTHER
    }
}