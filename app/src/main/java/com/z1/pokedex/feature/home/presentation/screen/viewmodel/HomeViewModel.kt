package com.z1.pokedex.feature.home.presentation.screen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.pokedex.core.network.repository.pokemonlist.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
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
            pokemonRepository.fetchPokemonPage(page)
                .catch { e ->
                    Log.d("TAG", e.message ?: "Algo deu errado")
                }
                .collect { newPage ->
                    _uiState.update {
                        it.copy(
                            pokemonPage = it.pokemonPage.copy(
                                count = newPage.count,
                                previousPage = newPage.previousPage,
                                nextPage = newPage.nextPage,
                                pokemonList = it.pokemonPage.pokemonList + newPage.pokemonList
                            )
                        )

                    }
                    _nextPage++
                }
        }
    }

    private fun loadNextPage() {
        fetchPokemonPage(_nextPage)
    }
}