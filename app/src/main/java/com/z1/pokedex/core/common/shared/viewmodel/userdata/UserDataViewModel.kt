package com.z1.pokedex.core.common.shared.viewmodel.userdata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.pokedex.core.common.shared.services.connectivity.ConnectivityService
import com.z1.pokedex.core.network.services.googleauth.GoogleAuthClient
import com.z1.pokedex.core.network.services.googlebilling.GoogleBillingClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserDataViewModel(
    private val googleAuthClient: GoogleAuthClient,
    googleBillingClient: GoogleBillingClient,
    connectivityService: ConnectivityService
) : ViewModel() {
    private val _state = MutableStateFlow(UserDataState())
    val state = combine(
        _state,
        googleAuthClient.signedInUser,
        googleBillingClient.subscriptionState,
        connectivityService.isConnected
    ) { state, signedInUser, subscriptionState, isConnected ->
        state.copy(
            data = signedInUser.data,
            message = signedInUser.message,
            subscriptionState = subscriptionState,
            isConnected = isConnected
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _state.value
    )

    fun onEvent(event: UserDataEvent) {
        when (event) {
            is UserDataEvent.GetSignedInUser -> googleAuthClient.getSignedInUser()
            is UserDataEvent.SignOut -> viewModelScope.launch { googleAuthClient.signOut() }
        }
    }
}