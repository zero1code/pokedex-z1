package com.z1.pokedex.core.database.repository.favorites

import android.graphics.drawable.Drawable
import com.z1.pokedex.core.database.model.PokemonEntity
import com.z1.pokedex.feature.home.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonFavoriteRepository {
    suspend fun getPokemonFavorites(userId: String): Flow<List<Pokemon>>
    suspend fun fetchPokemonImage(imageUrl: String): Drawable?
    suspend fun getPokemonFavoritesName(userId: String): Flow<List<String>>
    suspend fun insertPokemonFavorite(pokemon: Pokemon, userId: String): Long
    suspend fun deletePokemonFavorite(pokemon: Pokemon, userId: String): Int
}