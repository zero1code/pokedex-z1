package com.z1.pokedex.feature.home.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.pokedex.core.network.service.connectivity.ConnectivityService
import com.z1.pokedex.core.network.service.googleauth.GoogleAuthClient
import com.z1.pokedex.feature.home.domain.model.Pokemon
import com.z1.pokedex.feature.home.domain.usecase.PokemonUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class HomeViewModel(
    private val pokemonUseCase: PokemonUseCase,
    private val googleAuthClient: GoogleAuthClient,
    connectivityService: ConnectivityService
) : ViewModel() {
    private var _nextPage = 0

    private val _uiState = MutableStateFlow(UiState())
    val uiState = combine(_uiState, connectivityService.isConnected) { uiState, isConnected ->
        uiState.copy(
            isConnected = isConnected,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = _uiState.value
    )
    fun onEvent(event: Event) {
        when (event) {
            is Event.LoadNextPage -> loadNextPage()
            is Event.UpdateSelectedPokemon -> updateClickedPokemonList(event.pokemonName)
            is Event.GetPokemonDetails -> getPokemonDetails(event.pokemonName)
            is Event.SignedUser -> getSignedUser()
            is Event.Logout -> signOut()
            is Event.GetPokemonFavoritesName -> getPokemonFavoritesName()
            is Event.AddFavorite -> insertPokemonFavorite(event.pokemon)
            is Event.RemoveFavorite -> deletePokemonFavorite(event.pokemon)
        }
    }

    private fun fetchPokemonPage(page: Int = 0) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingPage = true,
                    isLastPage = false
                )
            }
            pokemonUseCase.fetchPokemonPage(page)
                .catch { e ->
                    e.printStackTrace()
                    _uiState.update {
                        it.copy(
                            isLoadingPage = false,
                            isFirstLoading = false,
                            isLastPage = e is UnknownHostException && it.isConnected.not()
                        )
                    }
                }
                .collect { newPage ->
                    _uiState.update {
                        it.copy(
                            pokemonPage = it.pokemonPage + newPage,
                            isLastPage = newPage.isEmpty(),
                            isLoadingPage = false,
                            isFirstLoading = false
                        )
                    }
                    _nextPage++
                }
        }
    }

    private fun updateClickedPokemonList(pokemonName: String) =
        viewModelScope.launch {
            _uiState.update {
                it.copy(pokemonClickedList = it.pokemonClickedList + pokemonName)
            }
        }

    private fun getPokemonDetails(pokemonName: String) =
        viewModelScope.launch {
            if (_uiState.value.pokemonDetails?.name == pokemonName) return@launch
            else resetPokemonDetails()

            pokemonUseCase.fetchPokemonDetails(pokemonName)
                .catch {
                    it.printStackTrace()
                }
                .collect { pokemonDetails ->
                    _uiState.update {
                        it.copy(pokemonDetails = pokemonDetails)
                    }
                }
        }

    private fun getPokemonFavoritesName() =
        viewModelScope.launch {
            pokemonUseCase.getPokemonFavoritesName(_uiState.value.userData?.userId.orEmpty())
                .catch { e -> e.printStackTrace() }
                .collect { favorites ->
                    _uiState.update {
                        it.copy(pokemonFavoritesNameList = favorites)
                    }
                }
        }

    private fun insertPokemonFavorite(pokemon: Pokemon) =
        viewModelScope.launch {
            pokemonUseCase.insertPokemonFavorite(pokemon, _uiState.value.userData?.userId.orEmpty())

        }

    private fun deletePokemonFavorite(pokemon: Pokemon) =
        viewModelScope.launch {
            pokemonUseCase.deletePokemonFavorite(pokemon, _uiState.value.userData?.userId.orEmpty())
        }

    private fun getSignedUser() = viewModelScope.launch {
        googleAuthClient.getSignedInUser()?.let { userData ->
            _uiState.update {
                it.copy(userData = userData)
            }
        }
    }

    private fun signOut() = viewModelScope.launch {
        googleAuthClient.signOut()
        _uiState.update {
            it.copy(userData = null)
        }
    }

    private fun resetPokemonDetails() =
        viewModelScope.launch {
            _uiState.update {
                it.copy(pokemonDetails = null)
            }
        }

    private fun loadNextPage() {
        fetchPokemonPage(_nextPage)
    }

    override fun onCleared() {
        super.onCleared()

    }
}