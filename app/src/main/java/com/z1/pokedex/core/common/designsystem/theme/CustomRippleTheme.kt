package com.z1.pokedex.core.common.designsystem.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

@Immutable
class CustomRippleTheme(private val color: Color): RippleTheme {
    @Composable
    @ReadOnlyComposable
    override fun defaultColor() = color

    @Composable
    @ReadOnlyComposable
    override fun rippleAlpha() = RippleAlpha(
        draggedAlpha = 0.7f,
        focusedAlpha = 0.7f,
        hoveredAlpha = 0.7f,
        pressedAlpha = 0.7f,
    )
}