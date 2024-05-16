package com.z1.pokedex.core.database.repository.favorites

import com.z1.pokedex.feature.home.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonFavoriteRepository {
    suspend fun getPokemonFavoritesName(userId: String): Flow<List<String>>
    suspend fun insertPokemonFavorite(pokemon: Pokemon, userId: String): Long
    suspend fun deletePokemonFavorite(pokemon: Pokemon, userId: String): Int
}