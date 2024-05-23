package com.z1.pokedex.feature.details.presentation

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.z1.pokedex.core.common.shared.viewmodel.userdata.UserDataViewModel
import com.z1.pokedex.feature.details.presentation.screen.PokemonDetailsScreen
import com.z1.pokedex.feature.details.presentation.screen.viewmodel.PokemonDetailsViewModel
import com.z1.pokedex.feature.home.domain.model.Pokemon
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PokemonDetailsContainer(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    onNavigationIconClick: () -> Unit,
    navigateToSubscriptionScreen: () -> Unit
) {
    val activity = LocalContext.current as ComponentActivity
    val userDataViewModel: UserDataViewModel = getViewModel(
        parameters = { parametersOf(activity) }
    )
    val userDataState = userDataViewModel.state.collectAsStateWithLifecycle()

    val viewModel: PokemonDetailsViewModel = getViewModel()
    val onEvent = viewModel::onEvent
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    PokemonDetailsScreen(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        pokemon = pokemon,
        userData = userDataState.value,
        uiState = uiState.value,
        onEvent = { newEvent -> onEvent(newEvent) },
        onNavigationIconClick = onNavigationIconClick,
        goToSubscriptionScreen = navigateToSubscriptionScreen
    )
}