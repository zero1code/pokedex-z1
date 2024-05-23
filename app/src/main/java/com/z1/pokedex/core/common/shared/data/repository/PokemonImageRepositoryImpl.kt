package com.z1.pokedex.core.common.shared.data.repository

import com.z1.pokedex.core.network.services.pokedex.PokedexClient
import com.z1.pokedex.core.common.shared.domain.repository.PokemonImageRepository

class PokemonImageRepositoryImpl(
    private val pokedexClient: PokedexClient
): PokemonImageRepository {
    override suspend fun fetchPokemonImage(imageUrl: String) =
        pokedexClient.fetchPokemonImage(imageUrl)
}