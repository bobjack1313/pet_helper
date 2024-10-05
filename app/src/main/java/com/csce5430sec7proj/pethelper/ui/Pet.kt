package com.csce5430sec7proj.pethelper.ui

data class Pet(
    val name: String,
    val type: String,
    val age: String = "Unknown", 
    val description: String = ""  
)
