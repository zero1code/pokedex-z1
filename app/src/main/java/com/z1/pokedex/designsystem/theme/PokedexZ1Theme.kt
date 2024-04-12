package com.z1.pokedex.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object PokedexZ1Theme {
    val dimen: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
}