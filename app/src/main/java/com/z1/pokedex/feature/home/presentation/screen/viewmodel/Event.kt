package com.z1.pokedex.feature.home.presentation.screen.viewmodel

sealed class Event {
    data object LoadNextPage: Event()
    data class UpdateSelectedPokemon(val pokemonName: String): Event()
    data class GetPokemonDetails(val pokemonName: String): Event()
    data object SignedUser: Event()
    data object Logout: Event()
}