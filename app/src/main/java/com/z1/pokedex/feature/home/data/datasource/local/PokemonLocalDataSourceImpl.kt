package com.z1.pokedex.feature.home.data.datasource.local

import com.z1.pokedex.core.database.dao.PokemonDao

class PokemonLocalDataSourceImpl(
    private val pokemonDao: PokemonDao
) : PokemonLocalDataSource {
    override suspend fun fetchPokemonPage(page: Int) =
        pokemonDao.getPokemonList(page)
            .takeIf { it.isNotEmpty() }
            ?: emptyList()
}