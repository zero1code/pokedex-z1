package com.z1.pokedex.feature.details.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.pokedex.core.common.shared.services.connectivity.ConnectivityService
import com.z1.pokedex.feature.details.domain.repository.PokemonDetailsRepository
import com.z1.pokedex.feature.details.presentation.screen.PokemonDetailsUiState
import com.z1.pokedex.feature.favorites.domain.repository.PokemonFavoriteRepository
import com.z1.pokedex.feature.home.domain.model.Pokemon
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val pokemonFavoriteRepository: PokemonFavoriteRepository,
    private val pokemonDetailsRepository: PokemonDetailsRepository,
    connectivityService: ConnectivityService
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonDetailsUiState())
    val uiState = combine(_uiState, connectivityService.isConnected) { uiState, isConnected ->
        uiState.copy(
            isConnected = isConnected,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = _uiState.value
    )

    fun onEvent(event: PokemonDetailsEvent) {
        when (event) {
            is PokemonDetailsEvent.GetPokemonFavoritesNameList -> getPokemonFavoritesNameList(event.userId)
            is PokemonDetailsEvent.GetPokemonPokemonDetails -> getPokemonDetails(event.pokemonName)
            is PokemonDetailsEvent.AddFavorite -> insertPokemonFavorite(event.pokemon, event.userId)
            is PokemonDetailsEvent.RemoveFavorite -> deletePokemonFavorite(
                event.pokemon,
                event.userId
            )
        }
    }

    private fun getPokemonFavoritesNameList(userId: String) =
        viewModelScope.launch {
            pokemonDetailsRepository.getPokemonFavoritesName(userId)
                .catch { e -> e.printStackTrace() }
                .collect { favoritesName ->
                    _uiState.update {
                        it.copy(
                            pokemonFavoritesNameList = favoritesName
                        )
                    }
                }
        }

    private fun getPokemonDetails(pokemonName: String) =
        viewModelScope.launch {
            if (_uiState.value.pokemonDetails?.name == pokemonName) return@launch
            else resetPokemonDetails()
            runCatching {
                async { pokemonDetailsRepository.fetchPokemonDetails(pokemonName) }.await()
            }.onSuccess { pokemonDetails ->
                _uiState.update {
                    it.copy(pokemonDetails = pokemonDetails)
                }
            }.onFailure { e ->
                e.printStackTrace()
            }
        }

    private fun insertPokemonFavorite(pokemon: Pokemon, userId: String) =
        viewModelScope.launch {
            pokemonFavoriteRepository.insertPokemonFavorite(
                pokemon,
                userId
            )
        }

    private fun deletePokemonFavorite(pokemon: Pokemon, userId: String) =
        viewModelScope.launch {
            pokemonFavoriteRepository.deletePokemonFavorite(
                pokemon,
                userId
            )
        }

    private fun resetPokemonDetails() =
        viewModelScope.launch {
            _uiState.update {
                it.copy(pokemonDetails = null)
            }
        }
}