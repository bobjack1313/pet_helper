package com.csce5430sec7proj.pethelper.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pet(
    val name: String,
    val type: String,
    val age: String = "Unknown",
    val description: String = ""
) : Parcelable
