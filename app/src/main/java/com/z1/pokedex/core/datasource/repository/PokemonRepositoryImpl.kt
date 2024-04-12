package com.z1.pokedex.core.datasource.repository

import android.util.Log
import com.z1.pokedex.feature.home.domain.mapper.PokemonPageMapper
import com.z1.pokedex.core.network.service.PokedexClient
import com.z1.pokedex.feature.home.domain.mapper.PokemonDetailsMapper
import com.z1.pokedex.feature.home.domain.model.PokemonDetails
import com.z1.pokedex.feature.home.domain.model.PokemonPage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

class PokemonRepositoryImpl(
    private val pokedexClient: PokedexClient,
    private val pokemonPageMapper: PokemonPageMapper,
    private val pokemonDetailsMapper: PokemonDetailsMapper
) : PokemonRepository {
    override suspend fun fetchPokemonPage(page: Int) =
        flow {
            emit(pokemonPageMapper.asModel(pokedexClient.fetchPokemonPage(page)))
        }


    override suspend fun fetchPokemonImage(imageUrl: String) =
        pokedexClient.fetchPokemonImage(imageUrl)

    override suspend fun fetchPokemonDetails(pokemonName: String) =
        flow {
            emit(pokemonDetailsMapper.asModel(pokedexClient.fetchPokemonDetails(pokemonName)))
        }
}