package com.z1.pokedex.feature.home.data.datasource.local

import com.z1.pokedex.core.database.model.PokemonEntity
import com.z1.pokedex.feature.home.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonLocalDataSource {
    suspend fun fetchPokemonPage(page: Int): List<PokemonEntity>
}