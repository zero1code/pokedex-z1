package com.z1.pokedex.feature.home.data.repository

import com.z1.pokedex.core.database.dao.PokemonDao
import com.z1.pokedex.feature.home.data.datasource.local.PokemonLocalDataSource
import com.z1.pokedex.feature.home.data.datasource.remote.PokemonRemoteDataSource
import com.z1.pokedex.feature.home.domain.repository.PokemonRepository
import com.z1.pokedex.feature.home.domain.repository.mapper.local.asEntity
import com.z1.pokedex.feature.home.domain.repository.mapper.local.asModel
import com.z1.pokedex.feature.home.domain.repository.mapper.remote.asModel
import kotlinx.coroutines.flow.flow

class PokemonRepositoryImpl(
    private val pokemonDao: PokemonDao,
    private val pokemonLocalDataSource: PokemonLocalDataSource,
    private val pokemonRemoteDataSource: PokemonRemoteDataSource
) : PokemonRepository {
    override suspend fun fetchPokemonPage(page: Int) =
        flow {
            val pokemonList = pokemonLocalDataSource.fetchPokemonPage(page)
                .takeIf { it.isNotEmpty() }
                ?.asModel()
                ?: pokemonRemoteDataSource.fetchPokemonPage(page)
                    .takeIf { it.isNotEmpty() }
                    ?.asModel()
                    ?.onEach { pokemon -> pokemon.page = page }
                    ?.also { pokemonDao.insertPokemonList(it.asEntity()) }
                ?: emptyList()

            emit(pokemonList)
        }
}