package com.z1.pokedex.feature.favorites.data.datasource.local

import com.z1.pokedex.core.database.model.PokemonFavoriteEntity
import kotlinx.coroutines.flow.Flow

interface PokemonFavoriteLocalDataSource {
    suspend fun getPokemonFavorites(userId: String): Flow<List<PokemonFavoriteEntity>>
}