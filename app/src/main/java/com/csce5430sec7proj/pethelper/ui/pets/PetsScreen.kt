package com.csce5430sec7proj.pethelper.ui.pets

import com.csce5430sec7proj.pethelper.ui.theme.Shapes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.csce5430sec7proj.pethelper.ui.Category
import com.csce5430sec7proj.pethelper.ui.pets.PetsViewModel
import com.csce5430sec7proj.pethelper.ui.Utils


@Composable
fun PetsScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit
) {
    val petsViewModel: PetsViewModel = viewModel()
    val petsState = petsViewModel.state

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
            modifier = Modifier.padding(paddingValues)
        ) {
            item {
                Spacer(modifier = Modifier.size(16.dp))
                LazyRow {
                    items(Utils.category) { category ->
                        CategoryItem(
                            iconRes = category.resId,
                            title = category.title,
                            selected = true,
                            //category == petsState.selectedCategory,
                            onItemClick = {
                                petsViewModel.onCategoryChange(category)
                            }
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    @DrawableRes iconRes: Int,
    title: String,
    selected: Boolean,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
            .selectable(
                selected = selected,
                interactionSource = MutableInteractionSource(),
                indication = null, // You don't need to manually specify the ripple here
                onClick = { onItemClick.invoke() }
            ),
        border = BorderStroke(
            1.dp,
            if (selected) MaterialTheme.colorScheme.primary.copy(.5f)
            else MaterialTheme.colorScheme.onSurface
        ),
        shape = Shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(.5f),
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium
            )
        }
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


