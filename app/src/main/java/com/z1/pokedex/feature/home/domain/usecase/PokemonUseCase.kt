package com.z1.pokedex.feature.home.domain.usecase

import com.z1.pokedex.feature.home.domain.model.Pokemon
import com.z1.pokedex.feature.home.domain.model.PokemonDetails
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {
    suspend fun fetchPokemonPage(page: Int): Flow<List<Pokemon>>
}