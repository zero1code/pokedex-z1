package com.z1.pokedex.feature.subscription.presentation

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.z1.pokedex.core.common.shared.viewmodel.userdata.UserDataViewModel
import com.z1.pokedex.feature.subscription.presentation.screen.SubscriptionScreen
import com.z1.pokedex.feature.subscription.presentation.screen.viewmodel.SubscriptionViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SubscriptionContainer(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit
) {
    val activity = LocalContext.current as ComponentActivity
    val userDataViewModel: UserDataViewModel = getViewModel(
        parameters = { parametersOf(activity) }
    )
    val userDataState = userDataViewModel.state.collectAsStateWithLifecycle()

    val viewModel: SubscriptionViewModel = getViewModel(
        parameters = { parametersOf(activity) }
    )
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    SubscriptionScreen(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background),
        userData = userDataState.value,
        uiState = uiState.value,
        onEvent = { newEvent -> onEvent(newEvent) },
        onNavigationIconClick = onNavigationIconClick
    )
}