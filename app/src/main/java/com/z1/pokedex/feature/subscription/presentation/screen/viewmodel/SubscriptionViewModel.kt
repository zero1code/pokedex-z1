package com.z1.pokedex.feature.subscription.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import com.z1.pokedex.core.network.service.connectivity.ConnectivityService
import com.z1.pokedex.core.network.service.googleauth.GoogleAuthClient
import com.z1.pokedex.feature.subscription.presentation.screen.SubscriptionScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SubscriptionViewModel(
    connectivityService: ConnectivityService
): ViewModel() {

    private var _uiState = MutableStateFlow(SubscriptionScreenUiState())
    val uiState get() = _uiState.asStateFlow()

    fun onEvent(subscriptionScreenEvent: SubscriptionScreenEvent) {
        when(subscriptionScreenEvent) {
            else -> {}
        }
    }
}