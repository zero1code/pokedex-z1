package com.z1.pokedex.feature.pro.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.z1.pokedex.feature.pro.presentation.screen.ProScreen

@Composable
fun ProContainer(
    modifier: Modifier = Modifier
) {
    ProScreen(
        onNavigationIconClick = {}
    )
}