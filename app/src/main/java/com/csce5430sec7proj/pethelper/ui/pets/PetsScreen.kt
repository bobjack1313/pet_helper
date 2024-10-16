package com.csce5430sec7proj.pethelper.ui.pets
//
import androidx.compose.foundation.Image
import com.csce5430sec7proj.pethelper.ui.theme.Shapes
//import androidx.annotation.DrawableRes
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.selection.selectable
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.csce5430sec7proj.pethelper.ui.Category
import com.csce5430sec7proj.pethelper.ui.pets.PetsViewModel
import com.csce5430sec7proj.pethelper.ui.Utils
//
//
//@Composable
//fun PetsScreen(
//    modifier: Modifier = Modifier,
//    onNavigate: (Int) -> Unit
//) {
//    val petsViewModel: PetsViewModel = viewModel()
//    val petsState = petsViewModel.state
//
//    Scaffold(
//        modifier = modifier,
//        floatingActionButton = {
//            FloatingActionButton(onClick = { onNavigate(-1) }) {
//                Icon(
//                    imageVector = Icons.Default.Add,
//                    contentDescription = "Add pet"
//                )
//            }
//        }
//    ) { paddingValues ->
//        LazyColumn(
//            modifier = Modifier.padding(paddingValues)
//            //modifier = Modifier.padding(2.dp)
//        ) {
//            item {
//                Spacer(modifier = Modifier.size(16.dp))
//                LazyRow {
//                    items(Utils.category) { category ->
//                        CategoryItem(
//                            iconRes = category.resId,
//                            title = category.title,
//                            selected = true,
//                            //category == petsState.selectedCategory,
//                            onItemClick = {
//                                petsViewModel.onCategoryChange(category)
//                            }
//                        )
//                        Spacer(modifier = Modifier.size(16.dp))
//                    }
//                }
//            }
//        }
//    }
// //   }
//}
//
//@Composable
//fun CategoryItem(
//    @DrawableRes iconRes: Int,
//    title: String,
//    selected: Boolean,
//    onItemClick: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
//            .selectable(
//                selected = selected,
//                interactionSource = MutableInteractionSource(),
//                indication = null, // You don't need to manually specify the ripple here
//                onClick = { onItemClick.invoke() }
//            ),
//        border = BorderStroke(
//            1.dp,
//            if (selected) MaterialTheme.colorScheme.primary.copy(.5f)
//            else MaterialTheme.colorScheme.onSurface
//        ),
//        shape = Shapes.large,
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.primary.copy(.5f),
//        )
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.padding(8.dp)
//        ) {
//            Icon(
//                painter = painterResource(id = iconRes),
//                contentDescription = null,
//                modifier = Modifier.size(24.dp)
//            )
//            Spacer(modifier = Modifier.size(8.dp))
//            Text(
//                text = title,
//                style = MaterialTheme.typography.labelMedium,
//                fontWeight = FontWeight.Medium
//            )
//        }
//    }
//}

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.data.entities.Pet
import java.sql.Date

@Composable
fun PetsScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit
) {
    // Using the view model
    val petsViewModel: PetsViewModel = viewModel()
    val petsState = petsViewModel.state.value

    // Temporary mock data for testing
    val mockPets = remember {
        mutableStateOf(
            listOf(
                Pet(
                    id = 1,
                    name = "Buddy",
                    breed = "Golden Retriever",
                    color = "Golden",
                    age = 3,
                    dateOfBirth = Date(0),  // Placeholder date (Unix epoch time)
                    aggression = 0.1
                ),
                Pet(
                    id = 2,
                    name = "Mittens",
                    color = "white",
                    age = 12,
                    dateOfBirth = Date(0),
                    breed = "Cat",
                    aggression = 0.0
                ),
                Pet(
                    id = 3,
                    name = "Charlie",
                    color = "white",
                    age = 12,
                    dateOfBirth = Date(0),
                    breed = "Fish",
                    aggression = 0.0
                )
            )
        )
    }

    // If the pets list is empty, show a centered "Add Pet" button
//    if (petsState.pets.isEmpty()) {
    // Check if pets list is empty and show "Add Pet" button in center
    if (mockPets.value.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = { onNavigate(-1) }) {
                Text("Add Pet")
            }
        }
    } else {
        // Otherwise, display the list of pets
        Scaffold(
            modifier = modifier,
            floatingActionButton = {
                FloatingActionButton(onClick = { onNavigate(-1) }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add pet"
                    )
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Use mock data for now to test the layout
                items(mockPets.value) { pet ->
                    PetRow(petName = pet.name)
                    Divider()
                }
            }
        }
    }
}

@Composable
fun PetRow(petName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Placeholder for pet image (replace with real image later)
        Image(
            painter = painterResource(id = R.drawable.pet_placeholder), // Placeholder image
            contentDescription = "Pet Picture",
            modifier = Modifier
                .size(100.dp)  // Set the size for the pet image
                .clip(CircleShape),  // Make the image circular
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))

        // Pet name text
        Text(
            text = petName,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 35.sp),
            color = Color.Black
        )
    }
}
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .background(Color(0xFF1976D2)),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Pets",
//            fontSize = 40.sp,
//            fontWeight = FontWeight.SemiBold,
//            color = Color.White
//        )
//
//    }
//}


