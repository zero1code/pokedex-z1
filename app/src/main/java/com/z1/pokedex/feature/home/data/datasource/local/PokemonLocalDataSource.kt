package com.z1.pokedex.feature.home.data.datasource.local

import com.z1.pokedex.core.database.model.PokemonEntity

interface PokemonLocalDataSource {
    suspend fun fetchPokemonPage(page: Int): List<PokemonEntity>
}