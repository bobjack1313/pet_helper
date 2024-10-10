package com.csce5430sec7proj.pethelper.ui.pets

//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//
//
//data class Pet1 (
//    val name: String,
//    val imageUrl: String
//)
//
//val petList = listOf(
//    Pet1("Betty", "https://betty.jpg"),
//    Pet1("Monty", "https://monty.jpg"),
//    Pet1("Lola", "https://lola.jpg")
//)
//
//
//@Composable
//fun PetTable(pets: List<Pet1>) {
//    LazyColumn {
//        items(pets) { pet ->
//
//            PetRow(pet)
//        }
//    }
//}
//
//@Composable
//fun AsyncImage(model: Any, contentDescription: String, modifier: Any, contentScale: Any) {
//
//}
//
//@Composable
//fun PetRow(pet: Pet1) {
//    Row(
//        modifier = Modifier
//            .padding(8.dp)
//            .fillMaxWidth()
//            .background(Color.LightGray)
//            .padding(16.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        // Load the image from URL using an image loading library like Coil or Glide
////        AsyncImage(
////            model = pet.imageUrl,
////            contentDescription = pet.name,
////            modifier = Modifier
////                .size(64.dp)
////                .clip(CircleShape),
////
////            contentScale = ContentScale.Crop
////        )
//        Spacer(modifier = Modifier.width(16.dp))
//        Text(text = pet.name, style = MaterialTheme.typography.headlineMedium)
//    }
//}
