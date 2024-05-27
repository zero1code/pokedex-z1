package com.z1.pokedex.core.common.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
interface IPokemonDimensions {
    val normal: Dp
    val headerSize: Dp
    val footerSize: Dp
    val brushShape: Dp
    val boxSize: Dp
    val imagePlaceHolderSize: Dp
    val canvaSize: Dp
    val imageSize: Dp
    val imageMarginEnd: Dp
    val offsetX: Dp
    val offsetY: Dp
}

@Immutable
internal data class PokemonDimensions(
    override val normal: Dp = 16.dp,
    override val headerSize: Dp = 100.dp,
    override val footerSize: Dp = 100.dp,
    override val brushShape: Dp = 30.dp,
    override val boxSize: Dp = 300.dp,
    override val imagePlaceHolderSize: Dp = 200.dp,
    override val canvaSize: Dp = 150.dp,
    override val imageSize: Dp = 150.dp,
    override val imageMarginEnd: Dp = 16.dp,
    override val offsetX: Dp = 5.dp,
    override val offsetY: Dp = 5.dp,

) : IPokemonDimensions

@Immutable
internal data class GridPokemonDimensions(
    override val normal: Dp = 8.dp,
    override val headerSize: Dp = 100.dp,
    override val footerSize: Dp = 100.dp,
    override val brushShape: Dp = 16.dp,
    override val boxSize: Dp = 150.dp,
    override val imagePlaceHolderSize: Dp = 100.dp,
    override val canvaSize: Dp = 50.dp,
    override val imageSize: Dp = 60.dp,
    override val imageMarginEnd: Dp = 16.dp,
    override val offsetX: Dp = 2.dp,
    override val offsetY: Dp = 2.dp,
) : IPokemonDimensions

internal val LocalPokemonSpacing = compositionLocalOf { PokemonDimensions() }
internal val LocalGridPokemonSpacing = compositionLocalOf { GridPokemonDimensions() }