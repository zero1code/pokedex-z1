package com.z1.pokedex.feature.home.domain.usecase

import com.z1.pokedex.feature.home.domain.model.PokemonDetails
import com.z1.pokedex.feature.home.domain.model.PokemonPage
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {
    suspend fun fetchPokemonPage(page: Int): Flow<PokemonPage>
    suspend fun fetchPokemonDetails(pokemonName: String): Flow<PokemonDetails>
}