package com.z1.pokedex.feature.details.presentation.screen.viewmodel

import com.z1.pokedex.feature.home.domain.model.Pokemon

sealed class PokemonDetailsEvent {
    data class GetPokemonPokemonDetails(val pokemonName: String) : PokemonDetailsEvent()
    data class GetPokemonFavoritesNameList(val userId: String) : PokemonDetailsEvent()
    data class AddFavorite(val pokemon: Pokemon) : PokemonDetailsEvent()
    data class RemoveFavorite(val pokemon: Pokemon) : PokemonDetailsEvent()
    data object SignedInUser : PokemonDetailsEvent()
}