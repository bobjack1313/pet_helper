package com.csce5430sec7proj.pethelper.ui.pets

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


@Composable
fun PetDetailsScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit,
    petId: String?
) {
    // Pet name text
    Text(
        text = "Name",
        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 35.sp),
        color = Color.Black
    )
}
