package com.csce5430sec7proj.pethelper.ui.theme

import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color


@Composable
fun PetHelperTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = Shapes
    )
}

private val DarkColorScheme = darkColorScheme(
    primary = HunyadiYellow,
    onPrimary = Color.Black,
    secondary = HunyadiYellow,
    onSecondary = Color.Black,
    tertiary = PaleDogwood,
    onTertiary = Color.Black,
    primaryContainer = Onyx,
    onPrimaryContainer = Color.White,
    secondaryContainer = OuterSpace,
    onSecondaryContainer = SecondaryWhite,
    tertiaryContainer = PaleDogwood,
    onTertiaryContainer = Color.Black,
    background = Color.Black,
    onBackground = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = BittersweetShimmer,
    onPrimary = Color.Black,
    secondary = BittersweetShimmer,
    onSecondary = Color.Black,
    tertiary = PaleDogwood,
    onTertiary = Color.Black,
    primaryContainer = Color(0xFFF2F2F2),
    onPrimaryContainer = Color.Black,
    secondaryContainer = Color(0xFFE6E6E6),
    onSecondaryContainer = SecondaryBlack,
    tertiaryContainer = PaleDogwood,
    onTertiaryContainer = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
)

val isLight: Boolean
    @Composable
    get() = MaterialTheme.colorScheme === LightColorScheme

val ColorScheme.topBarColor: Color
    @Composable
    get() = if (isLight) LightGrayTopBar else DarkGrayTopBar

fun logThemeColors() {
    Log.d("DefinedColors", "BittersweetShimmer: $BittersweetShimmer")
    Log.d("DefinedColors", "HunyadiYellow: $HunyadiYellow")
    Log.d("DefinedColors", "PaleDogwood: $PaleDogwood")
    Log.d("DefinedColors", "Onyx: $Onyx")
    Log.d("DefinedColors", "OuterSpace: $OuterSpace")
    Log.d("DefinedColors", "DarkGrayTopBar: $DarkGrayTopBar")
    Log.d("DefinedColors", "LightGrayTopBar: $LightGrayTopBar")
}