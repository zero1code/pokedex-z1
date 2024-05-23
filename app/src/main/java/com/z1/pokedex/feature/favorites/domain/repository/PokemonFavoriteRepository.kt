package com.z1.pokedex.feature.favorites.domain.repository

import com.z1.pokedex.feature.home.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonFavoriteRepository {
    suspend fun getPokemonFavorites(userId: String): Flow<List<Pokemon>>
    suspend fun insertPokemonFavorite(pokemon: Pokemon, userId: String): Long
    suspend fun deletePokemonFavorite(pokemon: Pokemon, userId: String): Int

}