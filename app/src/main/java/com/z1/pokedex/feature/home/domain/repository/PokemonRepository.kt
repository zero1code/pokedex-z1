package com.z1.pokedex.feature.home.domain.repository

import com.z1.pokedex.feature.home.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun fetchPokemonPage(page: Int): Flow<List<Pokemon>>
}