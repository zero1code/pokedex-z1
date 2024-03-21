package com.z1.pokedex.feature.utils

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
@Immutable
data class SharedElementData(
    val offsetX: Dp,
    val offsetY: Dp,
    val size: Dp,
) {
    companion object {
        val NONE = SharedElementData(0.dp, 0.dp, 0.dp)
    }
}
