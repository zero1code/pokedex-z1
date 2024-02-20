package com.z1.pokedex.feature.home.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.pokedex.core.network.model.NetworkResult
import com.z1.pokedex.core.network.repository.pokemonlist.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pokemonRepository: PokemonRepository,
) : ViewModel() {
    private var _nextPage = 0

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = _uiState.value
    )

    init {
        fetchPokemonPage()
    }

    fun onEvent(newEvent: Event) {
        when (newEvent) {
            is Event.LoadNextPage -> loadNextPage()
        }
    }

    private fun fetchPokemonPage(page: Int = 0) {
        viewModelScope.launch {
            when(val newPage = pokemonRepository.fetchPokemonPage(page)) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            pokemonPage = it.pokemonPage.copy(
                                count = newPage.data.count,
                                previousPage = newPage.data.previousPage,
                                nextPage = newPage.data.nextPage,
                                pokemonList = it.pokemonPage.pokemonList + newPage.data.pokemonList
                            )
                        )
                    }
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Exception -> {

                }
            }
        }
    }

    private fun loadNextPage() {
        _nextPage += 1
        fetchPokemonPage(_nextPage)
    }
}