package com.z1.pokedex.feature.subscription.presentation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.z1.pokedex.feature.subscription.presentation.screen.SubscriptionScreen
import com.z1.pokedex.feature.subscription.presentation.screen.viewmodel.SubscriptionViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun SubscriptionContainer(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit
) {
    val viewModel: SubscriptionViewModel = getViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    SubscriptionScreen(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background),
        subscriptionScreenUiState = uiState.value,
        onEvent = { newEvent -> onEvent(newEvent) },
        onNavigationIconClick = onNavigationIconClick
    )
}