package com.z1.pokedex.feature.utils

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

const val ANIM_DURATION = 500
@Stable
fun Int.toDp(density: Density): Dp = with(density) { this@toDp.toDp() }
@Stable
fun Float.toDp(density: Density): Dp = with(density) { this@toDp.toDp() }

@Stable
fun lerp(start: Color, stop: Color, fraction: Float): Color =
    androidx.compose.ui.graphics.lerp(start, stop, fraction)