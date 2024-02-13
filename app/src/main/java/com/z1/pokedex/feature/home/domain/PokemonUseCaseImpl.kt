package com.z1.pokedex.feature.home.domain

import com.z1.pokedex.feature.home.presentation.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class PokemonUseCaseImpl(
    
) : PokemonUseCase {
    override suspend fun fetchPokemonList(page: Int): Flow<Pokemon> {
        return emptyFlow()
    }
}