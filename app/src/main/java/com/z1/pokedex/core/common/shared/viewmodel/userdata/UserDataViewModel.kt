package com.z1.pokedex.core.common.shared.viewmodel.userdata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.pokedex.core.network.service.connectivity.ConnectivityService
import com.z1.pokedex.core.network.service.googleauth.GoogleAuthClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserDataViewModel(
    private val googleAuthClient: GoogleAuthClient,
    connectivityService: ConnectivityService
) : ViewModel() {
    private val _state = MutableStateFlow(UserDataState())
    val state = combine(
        _state,
        googleAuthClient.signedInUser,
        connectivityService.isConnected
    ) { state, signedInUser, isConnected ->
        state.copy(
            data = signedInUser.data,
            message = signedInUser.message,
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