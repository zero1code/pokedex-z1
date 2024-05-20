package com.z1.pokedex.feature.details.screen.viewmodel

import com.z1.pokedex.feature.home.domain.model.Pokemon

sealed class Event {
    data class GetPokemonDetails(val pokemonName: String) : Event()
    data class GetPokemonFavoritesNameList(val userId: String) : Event()
    data class AddFavorite(val pokemon: Pokemon) : Event()
    data class RemoveFavorite(val pokemon: Pokemon) : Event()
    data object SignedInUser : Event()
}