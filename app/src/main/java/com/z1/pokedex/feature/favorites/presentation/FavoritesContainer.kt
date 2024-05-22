package com.z1.pokedex.feature.favorites.presentation

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.z1.pokedex.core.common.shared.viewmodel.userdata.UserDataViewModel
import com.z1.pokedex.feature.favorites.presentation.screen.FavoritesScreen
import com.z1.pokedex.feature.favorites.presentation.screen.viewmodel.FavoritesViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun FavoritesContainer(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit
) {
    val activity = LocalContext.current as ComponentActivity
    val userDataViewModel: UserDataViewModel = getViewModel(
        parameters = { parametersOf(activity) }
    )
    val userDataState = userDataViewModel.state.collectAsStateWithLifecycle()

    val viewModel: FavoritesViewModel = getViewModel()
    val onEvent = viewModel::onEvent
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    FavoritesScreen(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        userData = userDataState.value,
        uiState = uiState.value,
        onEvent = { newEvent -> onEvent(newEvent) },
        onNavigationIconClick = onNavigationIconClick
    )
}