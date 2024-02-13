package com.z1.pokedex.feature.home.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.pokedex.feature.home.domain.PokemonUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pokemonUseCase: PokemonUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = _uiState.value
    )

    init {
        fetchPokemonList()
    }

    fun onEvent(newEvent: Event) {
        when(newEvent) {
            else -> Unit
        }
    }

    private fun fetchPokemonList() =
        viewModelScope.launch {
            pokemonUseCase.fetchPokemonList(0)
                .onStart {

                }
                .onCompletion {

                }
                .catch {

                }
                .collect {

                }
        }
}