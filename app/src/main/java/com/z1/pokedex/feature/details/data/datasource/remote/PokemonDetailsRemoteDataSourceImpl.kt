package com.z1.pokedex.feature.details.data.datasource.remote

import com.z1.pokedex.core.network.services.pokedex.PokedexClient

class PokemonDetailsRemoteDataSourceImpl(
    private val pokedexClient: PokedexClient
) : PokemonDetailsRemoteDataSource {
    override suspend fun fetchPokemonDetails(pokemonName: String) =
        pokedexClient.fetchPokemonDetails(pokemonName)
}