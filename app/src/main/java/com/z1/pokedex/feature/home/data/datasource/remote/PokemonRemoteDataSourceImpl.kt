package com.z1.pokedex.feature.home.data.datasource.remote

import com.z1.pokedex.core.network.services.pokedex.PokedexClient

class PokemonRemoteDataSourceImpl(
    private val pokedexClient: PokedexClient
) : PokemonRemoteDataSource {
    override suspend fun fetchPokemonPage(page: Int) =
        pokedexClient.fetchPokemonPage(page)
            .takeIf { it.nextPage != null }
            ?.results
            ?: emptyList()
}