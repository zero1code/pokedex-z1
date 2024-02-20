package com.z1.pokedex.core.network.repository.pokemonlist

import com.z1.pokedex.core.network.model.NetworkResult
import com.z1.pokedex.feature.home.presentation.model.PokemonPage

interface PokemonRepository {
    suspend fun fetchPokemonPage(page: Int): NetworkResult<PokemonPage>
}