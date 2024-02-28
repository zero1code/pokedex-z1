package com.z1.pokedex.core.network.repository.pokemonlist

import com.z1.pokedex.core.network.datasource.PokemonPageDataSource
import com.z1.pokedex.feature.home.presentation.model.PokemonPage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PokemonRepositoryImpl(
    private val pokemonPageDataSource: PokemonPageDataSource,
    private val dispatcher: CoroutineDispatcher
) : PokemonRepository {
    override suspend fun fetchPokemonPage(page: Int): Flow<PokemonPage> =
        flow { emit(pokemonPageDataSource.fetchPokemonPage(page)) }
            .flowOn(dispatcher)
}