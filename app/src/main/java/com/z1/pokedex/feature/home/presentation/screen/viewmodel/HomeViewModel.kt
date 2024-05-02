package com.z1.pokedex.feature.home.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.pokedex.core.network.service.googleauth.GoogleAuthClient
import com.z1.pokedex.feature.home.domain.usecase.PokemonUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pokemonUseCase: PokemonUseCase,
    private val googleAuthClient: GoogleAuthClient
) : ViewModel() {
    private var _nextPage = 0

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.stateIn(
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
        }
    }

    private fun fetchPokemonPage(page: Int = 0) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingPage = true) }
            pokemonUseCase.fetchPokemonPage(page)
                .catch { e ->
                    e.printStackTrace()
                    _uiState.update {
                        it.copy(
                            isLoadingPage = false,
                            isFirstLoading = false
                        )
                    }
                }
                .collect { newPage ->
                    _uiState.update {
                        it.copy(
                            pokemonPage = it.pokemonPage.copy(
                                count = newPage.count,
                                previousPage = newPage.previousPage,
                                nextPage = newPage.nextPage,
                                pokemonList = it.pokemonPage.pokemonList + newPage.pokemonList
                            ),
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
}