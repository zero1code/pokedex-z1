package com.z1.pokedex.feature.subscription.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.pokedex.core.network.service.googleauth.GoogleAuthClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubscriptionViewModel(private val googleAuthClient: GoogleAuthClient): ViewModel() {

    private var _uiState = MutableStateFlow(UiState())
    val uiState get() = _uiState.asStateFlow()


    fun onEvent(event: Event) {
        when(event) {
            Event.SignedUser -> getSignedInUser()
        }
    }

    private fun getSignedInUser() = viewModelScope.launch {
        googleAuthClient.getSignedInUser().apply {
            _uiState.update {
                it.copy(userData = this)
            }
        }
    }
}