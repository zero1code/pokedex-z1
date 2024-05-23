package com.z1.pokedex.feature.home.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.pokedex.core.common.shared.services.connectivity.ConnectivityService
import com.z1.pokedex.feature.home.domain.model.Pokemon
import com.z1.pokedex.feature.home.domain.usecase.PokemonUseCase
import com.z1.pokedex.feature.home.presentation.screen.HomeScreenUiState
import kotlinx.coroutines.delay
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
    connectivityService: ConnectivityService
) : ViewModel() {
    private var _nextPage = 0

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState = combine(_uiState, connectivityService.isConnected) { uiState, isConnected ->
        uiState.copy(
            isConnected = isConnected,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = _uiState.value
    )
    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.LoadNextPage -> loadNextPage()
            is HomeScreenEvent.PokemonClicked -> lastPokemonClicked(event.pokemon)
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

    private fun updateSelectedPokemonList(pokemonName: String) =
        viewModelScope.launch {
            delay(500)
            _uiState.update {
                it.copy(pokemonClickedList = it.pokemonClickedList + pokemonName)
            }
        }

    private fun lastPokemonClicked(pokemon: Pokemon?) =
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    lastPokemonClicked = pokemon
                )
            }
            pokemon?.let {
                updateSelectedPokemonList(pokemon.name)
            }
        }

    private fun loadNextPage() {
        fetchPokemonPage(_nextPage)
    }
}