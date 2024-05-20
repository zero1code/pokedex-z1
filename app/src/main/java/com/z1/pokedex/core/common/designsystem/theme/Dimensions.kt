package com.z1.pokedex.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Dimensions(
    val normal: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val big: Dp = 24.dp,
    val large: Dp = 32.dp,
    val large2: Dp = 56.dp,
    val topBar: Dp = 80.dp,
    val loadingIcon: Dp = 100.dp
)

internal val LocalSpacing = compositionLocalOf { Dimensions() }
