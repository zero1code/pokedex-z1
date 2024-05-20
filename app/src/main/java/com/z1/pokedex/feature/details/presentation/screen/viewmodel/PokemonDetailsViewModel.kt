package com.z1.pokedex.feature.details.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.pokedex.core.database.repository.favorites.PokemonFavoriteRepository
import com.z1.pokedex.core.network.service.connectivity.ConnectivityService
import com.z1.pokedex.core.network.service.googleauth.GoogleAuthClient
import com.z1.pokedex.core.network.service.pokedex.repository.PokemonDetailsRepository
import com.z1.pokedex.feature.details.presentation.screen.PokemonDetailsUiState
import com.z1.pokedex.feature.home.domain.model.Pokemon
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
    private val googleAuthClient: GoogleAuthClient,
    connectivityService: ConnectivityService
) : ViewModel() {

    private val _Details_uiState = MutableStateFlow(PokemonDetailsUiState())
    val uiState = combine(_Details_uiState, connectivityService.isConnected) { uiState, isConnected ->
        uiState.copy(
            isConnected = isConnected,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = _Details_uiState.value
    )

    fun onEvent(pokemonDetailsEvent: PokemonDetailsEvent) {
        when (pokemonDetailsEvent) {
            is PokemonDetailsEvent.SignedInUser -> getSignedInUser()
            is PokemonDetailsEvent.GetPokemonFavoritesNameList -> getPokemonFavoritesNameList(pokemonDetailsEvent.userId)
            is PokemonDetailsEvent.GetPokemonPokemonDetails -> getPokemonDetails(pokemonDetailsEvent.pokemonName)
            is PokemonDetailsEvent.AddFavorite -> insertPokemonFavorite(pokemonDetailsEvent.pokemon)
            is PokemonDetailsEvent.RemoveFavorite -> deletePokemonFavorite(pokemonDetailsEvent.pokemon)
        }
    }

    private fun getSignedInUser() = viewModelScope.launch {
        googleAuthClient.getSignedInUser()?.let { userData ->
            _Details_uiState.update {
                it.copy(userData = userData)
            }
        }
    }

    private fun getPokemonFavoritesNameList(userId: String) =
        viewModelScope.launch {
            pokemonFavoriteRepository.getPokemonFavoritesName(userId)
                .catch { e -> e.printStackTrace() }
                .collect { favoritesName ->
                    _Details_uiState.update {
                        it.copy(
                            pokemonFavoritesNameList = favoritesName
                        )
                    }
                }
        }

    private fun getPokemonDetails(pokemonName: String) =
        viewModelScope.launch {
            if (_Details_uiState.value.pokemonDetails?.name == pokemonName) return@launch
            else resetPokemonDetails()
            pokemonDetailsRepository.fetchPokemonDetails(pokemonName)
                .catch {
                    it.printStackTrace()
                }
                .collect { pokemonDetails ->
                    _Details_uiState.update {
                        it.copy(pokemonDetails = pokemonDetails)
                    }
                }

        }

    private fun insertPokemonFavorite(pokemon: Pokemon) =
        viewModelScope.launch {
            pokemonFavoriteRepository.insertPokemonFavorite(
                pokemon,
                _Details_uiState.value.userData?.userId.orEmpty()
            )
        }

    private fun deletePokemonFavorite(pokemon: Pokemon) =
        viewModelScope.launch {
            pokemonFavoriteRepository.deletePokemonFavorite(
                pokemon,
                _Details_uiState.value.userData?.userId.orEmpty()
            )
        }

    private fun resetPokemonDetails() =
        viewModelScope.launch {
            _Details_uiState.update {
                it.copy(pokemonDetails = null)
            }
        }
}