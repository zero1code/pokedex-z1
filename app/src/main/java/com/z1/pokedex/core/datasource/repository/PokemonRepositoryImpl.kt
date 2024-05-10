package com.z1.pokedex.core.datasource.repository

import com.z1.pokedex.core.database.dao.PokemonDao
import com.z1.pokedex.core.database.mapper.asEntity
import com.z1.pokedex.core.database.mapper.asModel
import com.z1.pokedex.core.network.mapper.asModel
import com.z1.pokedex.core.network.service.PokedexClient
import com.z1.pokedex.feature.home.domain.model.Pokemon
import kotlinx.coroutines.flow.flow

class PokemonRepositoryImpl(
    private val pokedexClient: PokedexClient,
    private val pokemonDao: PokemonDao
) : PokemonRepository {
    override suspend fun fetchPokemonPage(page: Int) =
        flow {
            var pokemonList = getPokemonListFromDatabase(page)
            if (pokemonList.isEmpty()) {
                pokemonList = fetchAndInsertPokemonPage(page)
            }
            emit(pokemonList)
        }

    override suspend fun fetchPokemonImage(imageUrl: String) =
        pokedexClient.fetchPokemonImage(imageUrl)

    override suspend fun fetchPokemonDetails(pokemonName: String) =
        flow {
            emit(pokedexClient.fetchPokemonDetails(pokemonName).asModel())
        }

    private suspend fun getPokemonListFromDatabase(page: Int) =
        pokemonDao.getPokemonList(page).asModel()

    private suspend fun fetchAndInsertPokemonPage(page: Int): List<Pokemon> {
        val response = pokedexClient.fetchPokemonPage(page)
        val pokemonList = response.results.asModel()
        pokemonList.forEach { pokemon -> pokemon.page = page }
        pokemonDao.insertPokemonList(pokemonList.asEntity())
        return if (response.nextPage != null) pokemonDao.getPokemonList(page).asModel()
        else emptyList()
    }
}