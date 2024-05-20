package com.z1.pokedex.feature.subscription.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.pokedex.core.network.service.googleauth.GoogleAuthClient
import com.z1.pokedex.feature.subscription.presentation.screen.SubscriptionScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubscriptionViewModel(private val googleAuthClient: GoogleAuthClient): ViewModel() {

    private var _SubscriptionScreen_uiState = MutableStateFlow(SubscriptionScreenUiState())
    val uiState get() = _SubscriptionScreen_uiState.asStateFlow()


    fun onEvent(subscriptionScreenEvent: SubscriptionScreenEvent) {
        when(subscriptionScreenEvent) {
            SubscriptionScreenEvent.SignedUser -> getSignedInUser()
        }
    }

    private fun getSignedInUser() = viewModelScope.launch {
        googleAuthClient.getSignedInUser().apply {
            _SubscriptionScreen_uiState.update {
                it.copy(userData = this)
            }
        }
    }
}