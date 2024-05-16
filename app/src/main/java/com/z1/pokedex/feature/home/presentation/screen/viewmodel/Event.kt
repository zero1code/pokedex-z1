package com.z1.pokedex.feature.home.presentation.screen.viewmodel

import com.z1.pokedex.feature.home.domain.model.Pokemon

sealed class Event {
    data object LoadNextPage: Event()
    data class UpdateSelectedPokemon(val pokemonName: String): Event()
    data class GetPokemonDetails(val pokemonName: String): Event()
    data object SignedUser: Event()
    data object Logout: Event()
    data object GetPokemonFavoritesName: Event()
    data class AddFavorite(val pokemon: Pokemon): Event()
    data class RemoveFavorite(val pokemon: Pokemon): Event()
}