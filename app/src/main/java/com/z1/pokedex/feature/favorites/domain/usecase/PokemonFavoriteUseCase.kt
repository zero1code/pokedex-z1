package com.z1.pokedex.feature.favorites.domain.usecase

import com.z1.pokedex.feature.home.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonFavoriteUseCase {
    suspend fun getPokemonFavorites(userId: String): Flow<List<Pokemon>>
}