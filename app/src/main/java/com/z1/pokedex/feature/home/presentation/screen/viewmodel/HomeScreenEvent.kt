package com.z1.pokedex.feature.home.presentation.screen.viewmodel

import com.z1.pokedex.feature.home.domain.model.Pokemon

sealed class HomeScreenEvent {
    data object LoadNextPage : HomeScreenEvent()
    data class PokemonClicked(val pokemon: Pokemon?) : HomeScreenEvent()
}