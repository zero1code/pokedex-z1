package com.z1.pokedex.feature.details.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface PokemonDetailsLocalDataSource {
    suspend fun getPokemonFavoritesName(userId: String): Flow<List<String>>
}