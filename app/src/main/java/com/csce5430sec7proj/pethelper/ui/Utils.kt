package com.csce5430sec7proj.pethelper.ui

import androidx.annotation.DrawableRes
import com.csce5430sec7proj.pethelper.R

object Utils {
    val category = listOf(
        Category(title = "Dogs", resId = R.drawable.ic_dog, id = 0),
        Category(title = "Cats", resId = R.drawable.ic_cat, id = 1),
        Category(title = "Birds", resId = R.drawable.ic_bird, id = 2),
        Category(title = "Fish", resId = R.drawable.ic_fish, id = 3),
        Category(title = "Lizards", resId = R.drawable.ic_lizard, id = 4),
        Category(title = "Snakes", resId = R.drawable.ic_snake, id = 5),
        Category(title = "Hamsters", resId = R.drawable.ic_hamster, id = 6),
        Category(title = "Gerbils", resId = R.drawable.ic_gerbil, id = 7),
        Category(title = "Horses", resId = R.drawable.ic_horse, id = 8),
        Category(title = "Pigs", resId = R.drawable.ic_pig, id = 9),
        Category(title = "Sheep", resId = R.drawable.ic_sheep, id = 10),
        Category(title = "Goats", resId = R.drawable.ic_goat, id = 11),
        Category(title = "Cows", resId = R.drawable.ic_cow, id = 12),
        Category(title = "None", resId =R.drawable.ic_dog ,id = 10001)
    )
}

data class Category(
    @DrawableRes val resId: Int = -1,
    val title: String = "",
    val id: Int = -1,
)