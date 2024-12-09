package com.csce5430sec7proj.pethelper.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.data.entities.PetType


@Composable
fun PetTypeImage(
    petType: PetType,
    modifier: Modifier = Modifier.size(50.dp),
    tint: Color = Color.Unspecified
) {
    val (imageRes, description) = when (petType) {
        PetType.DOG -> R.drawable.ic_dog to "Dog"
        PetType.CAT -> R.drawable.ic_cat to "Cat"
        PetType.FISH -> R.drawable.ic_fish to "Fish"
        PetType.BIRD -> R.drawable.ic_bird to "Bird"
        PetType.REPTILE -> R.drawable.pet_placeholder to "Reptile"
        PetType.SNAKE -> R.drawable.ic_snake to "Snake"
        PetType.LIZARD -> R.drawable.ic_lizard to "Lizard"
        PetType.TURTLE -> R.drawable.pet_placeholder to "Turtle"
        PetType.GERBIL -> R.drawable.ic_gerbil to "Gerbil"
        PetType.HAMSTER -> R.drawable.ic_hamster to "Hamster"
        PetType.PIG -> R.drawable.ic_pig to "Pig"
        PetType.CHICKEN -> R.drawable.pet_placeholder to "Chicken"
        PetType.GOAT -> R.drawable.ic_goat to "Goat"
        PetType.SHEEP -> R.drawable.ic_sheep to "Sheep"
        PetType.BOAR -> R.drawable.pet_placeholder to "Boar"
        PetType.COW -> R.drawable.ic_cow to "Cow"
        PetType.HORSE -> R.drawable.ic_horse to "Horse"
        PetType.RABBIT -> R.drawable.pet_placeholder to "Rabbit"
        PetType.RAT -> R.drawable.pet_placeholder to "Rat"
        PetType.INSECT -> R.drawable.pet_placeholder to "Insect"
        PetType.SPIDER -> R.drawable.pet_placeholder to "Spider"
        PetType.WORM -> R.drawable.pet_placeholder to "Worm"
        PetType.OTHER -> R.drawable.ic_pet_placeholder to "Other"
    }

    Icon(
        painter = painterResource(id = imageRes),
        contentDescription = description,
        modifier = modifier,
        tint = tint
    )
}