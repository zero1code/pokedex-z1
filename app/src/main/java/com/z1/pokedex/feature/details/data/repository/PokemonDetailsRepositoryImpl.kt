package com.z1.pokedex.feature.details.data.repository

import com.z1.pokedex.feature.details.data.datasource.local.PokemonDetailsLocalDataSource
import com.z1.pokedex.feature.details.data.datasource.remote.PokemonDetailsRemoteDataSource
import com.z1.pokedex.feature.details.domain.repository.PokemonDetailsRepository
import com.z1.pokedex.feature.details.domain.repository.mapper.asModel
import kotlinx.coroutines.flow.flow

class PokemonDetailsRepositoryImpl(
    private val pokemonDetailsLocalDataSource: PokemonDetailsLocalDataSource,
    private val pokemonDetailsRemoteDataSource: PokemonDetailsRemoteDataSource
) : PokemonDetailsRepository {
    override suspend fun getPokemonFavoritesName(userId: String) =
        flow {
            pokemonDetailsLocalDataSource.getPokemonFavoritesName(userId)
                .collect {
                    emit(it)
                }
        }

    override suspend fun fetchPokemonDetails(pokemonName: String) =
        pokemonDetailsRemoteDataSource.fetchPokemonDetails(pokemonName).asModel()
}