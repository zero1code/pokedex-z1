package com.z1.pokedex.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.z1.pokedex.feature.home.presentation.screen.HomeScreen
import com.z1.pokedex.feature.home.presentation.screen.viewmodel.HomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeContainer(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit,
    drawerNavigateTo: (String) -> Unit
) {
    val viewmodel: HomeViewModel = getViewModel()
    val uiState = viewmodel.uiState.collectAsStateWithLifecycle()
    val onEvent = viewmodel::onEvent

    HomeScreen(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background),
        uiState = uiState.value,
        onEvent = { newEvent -> onEvent(newEvent) },
        navigateToLogin = navigateToLogin,
        drawerNavigation = { route ->
            drawerNavigateTo(route)
        }
    )
}