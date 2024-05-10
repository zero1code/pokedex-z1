package com.z1.pokedex.designsystem.extensions

import androidx.compose.ui.Modifier

fun Modifier.thenIf(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier =
    if (condition) then(modifier(Modifier))
    else this