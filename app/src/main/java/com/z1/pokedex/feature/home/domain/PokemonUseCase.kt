package com.z1.pokedex.feature.home.domain

import com.z1.pokedex.feature.home.presentation.model.PokemonPage
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {
    suspend fun fetchPokemonPage(page: Int): Flow<PokemonPage>
}