package com.z1.pokedex.feature.home.presentation.screen.viewmodel

sealed class HomeScreenEvent {
    data object LoadNextPage: HomeScreenEvent()
    data object SignedUser: HomeScreenEvent()
    data object Logout: HomeScreenEvent()
    data class UpdateSelectedPokemon(val pokemonName: String) : HomeScreenEvent()
}