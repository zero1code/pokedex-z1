package com.z1.pokedex.feature.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.z1.pokedex.feature.home.presentation.screen.HomeScreen
import com.z1.pokedex.feature.home.presentation.screen.viewmodel.HomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun MainContainer(
    modifier: Modifier = Modifier
) {
    val viewmodel: HomeViewModel = getViewModel()
    val uiState = viewmodel.uiState.collectAsStateWithLifecycle()
    val onEvent = viewmodel::onEvent

    HomeScreen(
        modifier = modifier,
        uiState = uiState.value,
    ) { newEvent -> onEvent(newEvent) }
}