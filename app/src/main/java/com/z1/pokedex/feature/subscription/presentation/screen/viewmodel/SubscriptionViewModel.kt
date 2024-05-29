package com.z1.pokedex.feature.subscription.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.pokedex.core.common.shared.services.connectivity.ConnectivityService
import com.z1.pokedex.feature.subscription.domain.repository.SubscriptionRepository
import com.z1.pokedex.feature.subscription.presentation.screen.SubscriptionScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubscriptionViewModel(
    private val subscriptionRepository: SubscriptionRepository,
    connectivityService: ConnectivityService
) : ViewModel() {

    private var _uiState = MutableStateFlow(SubscriptionScreenUiState())
    val uiState get() = _uiState.asStateFlow()

    fun onEvent(event: SubscriptionScreenEvent) {
        when (event) {
            is SubscriptionScreenEvent.Subscribe -> subscribe(event.userId)
        }
    }

    private fun subscribe(userId: String) = viewModelScope.launch {
        subscriptionRepository.checkSubscriptionStatus("premium", userId)
        _uiState.update {
            it.copy(userClickedInSubscriptionButton = true)
        }
    }
}