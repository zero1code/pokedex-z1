package com.z1.pokedex.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

object PokedexZ1Theme {
    val dimen: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

    val verticalList: IPokemonDimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalPokemonSpacing.current

    val gridList: IPokemonDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalGridPokemonSpacing.current
}