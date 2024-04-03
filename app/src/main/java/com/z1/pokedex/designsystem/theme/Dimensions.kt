package com.z1.pokedex.designsystem.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val normal: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val big: Dp = 24.dp,
    val loadingIcon: Dp = 100.dp
)

val LocalSpacing = compositionLocalOf { Dimensions() }
