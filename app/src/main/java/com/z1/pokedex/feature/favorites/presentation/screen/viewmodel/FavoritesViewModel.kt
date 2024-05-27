package com.z1.pokedex.feature.favorites.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.pokedex.core.common.shared.services.connectivity.ConnectivityService
import com.z1.pokedex.feature.favorites.domain.usecase.PokemonFavoriteUseCase
import com.z1.pokedex.feature.favorites.presentation.screen.FavoritesScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val pokemonFavoriteUseCase: PokemonFavoriteUseCase,
    connectivityService: ConnectivityService
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesScreenUiState())
    val uiState = combine(_uiState, connectivityService.isConnected) { uiState, isConnected ->
        uiState.copy(
            isConnected = isConnected,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _uiState.value
    )

    fun onEvent(event: FavoritesScreenEvent) {
        when (event) {
            is FavoritesScreenEvent.GetFavorites -> getFavorites(event.userId)
        }
    }

    private fun getFavorites(userId: String) =
        viewModelScope.launch {
            pokemonFavoriteUseCase.getPokemonFavorites(userId)
                .catch { e -> e.printStackTrace() }
                .collect { favorites ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            favorites = favorites
                        )
                    }
                }
        }
}