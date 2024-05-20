package com.z1.pokedex.feature.favorites.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.pokedex.core.network.service.connectivity.ConnectivityService
import com.z1.pokedex.core.network.service.googleauth.GoogleAuthClient
import com.z1.pokedex.feature.favorites.domain.usecase.PokemonFavoriteUseCase
import com.z1.pokedex.feature.favorites.presentation.screen.FavoritesScreenUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val pokemonFavoriteUseCase: PokemonFavoriteUseCase,
    private val googleAuthClient: GoogleAuthClient,
    connectivityService: ConnectivityService
): ViewModel() {

    private val _FavoritesScreen_uiState = MutableStateFlow(FavoritesScreenUiState())
    val uiState = combine(_FavoritesScreen_uiState, connectivityService.isConnected) { uiState, isConnected ->
        uiState.copy(
            isConnected = isConnected,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _FavoritesScreen_uiState.value
    )

    fun onEvent(favoritesScreenEvent: FavoritesScreenEvent) {
        when(favoritesScreenEvent) {
            is FavoritesScreenEvent.GetFavorites -> getFavorites(favoritesScreenEvent.userId)
            is FavoritesScreenEvent.SignedInUser -> getSignedInUser()
        }
    }

    private fun getSignedInUser() = viewModelScope.launch {
        googleAuthClient.getSignedInUser()?.let { userData ->
            _FavoritesScreen_uiState.update {
                it.copy(userData = userData)
            }
        }
    }

    private fun getFavorites(userId: String) =
        viewModelScope.launch {
            delay(2000)
            pokemonFavoriteUseCase.getPokemonFavorites(userId)
                .catch { e -> e.printStackTrace() }
                .collect { favorites ->
                    _FavoritesScreen_uiState.update {
                        it.copy(
                            isLoading = false,
                            favorites = favorites
                        )
                    }
                }
        }
}