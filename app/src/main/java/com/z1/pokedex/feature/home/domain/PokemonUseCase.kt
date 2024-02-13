package com.z1.pokedex.feature.home.domain

import com.z1.pokedex.feature.home.presentation.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {
    suspend fun fetchPokemonList(page: Int): Flow<Pokemon>
}