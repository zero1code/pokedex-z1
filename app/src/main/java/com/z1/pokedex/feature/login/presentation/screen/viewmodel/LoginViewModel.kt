package com.z1.pokedex.feature.login.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import com.z1.pokedex.core.network.model.google.SignResult
import com.z1.pokedex.navigation.navgraph.routes.home.HomeNavGraph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(
    private val loginNavGraph: HomeNavGraph
): ViewModel() {

    private var _state = MutableStateFlow(SignInState())
    val state get() = _state.asStateFlow()

    fun onSignInResult(result: SignResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }
}