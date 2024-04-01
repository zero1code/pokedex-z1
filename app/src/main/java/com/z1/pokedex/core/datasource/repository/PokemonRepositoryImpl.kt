package com.z1.pokedex.core.datasource.repository

import com.z1.pokedex.core.network.mapper.PokemonPageMapper
import com.z1.pokedex.core.network.service.PokedexClient
import com.z1.pokedex.feature.home.domain.model.PokemonPage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonRepositoryImpl(
    private val pokedexClient: PokedexClient,
    private val pokemonPageMapper: PokemonPageMapper
): PokemonRepository {
    override suspend fun fetchPokemonPage(page: Int): Flow<PokemonPage> {
        return flow {
            emit(
                pokemonPageMapper.mapDtoToModel(pokedexClient.fetchPokemonPage(page))
            )
        }
    }

    override suspend fun fetchPokemonImage(imageUrl: String) =
        pokedexClient.fetchPokemonImage(imageUrl)
}