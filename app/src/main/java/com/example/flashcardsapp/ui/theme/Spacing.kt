package com.example.flashcardsapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val default: Dp = 0.dp,
    val extraSmall: Dp = 4.dp,
    val extraSmallToSmall: Dp = 6.dp,
    val small: Dp = 8.dp,
    val smallToMedium: Dp = 10.dp,
    val medium: Dp = 16.dp,
    val mediumToLarge: Dp = 20.dp,
    val almostLarge: Dp = 30.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 64.dp
)

val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
