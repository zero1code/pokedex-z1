package com.z1.pokedex.core.database.repository.favorites

import com.z1.pokedex.core.database.dao.PokemonDao
import com.z1.pokedex.core.database.mapper.asFavoriteEntity
import com.z1.pokedex.core.database.mapper.asModel
import com.z1.pokedex.core.network.services.pokedex.PokedexClient
import com.z1.pokedex.feature.home.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonFavoriteRepositoryImpl(
    private val pokedexClient: PokedexClient,
    private val pokemonDao: PokemonDao
) : PokemonFavoriteRepository {
    override suspend fun getPokemonFavorites(userId: String) =
        flow {
            pokemonDao.getPokemonFavoriteList(userId).collect {
                emit(it.asModel())
            }
        }

    override suspend fun fetchPokemonImage(imageUrl: String) =
        pokedexClient.fetchPokemonImage(imageUrl)

    override suspend fun getPokemonFavoritesName(userId: String): Flow<List<String>> =
        flow {
            pokemonDao.getPokemonFavoriteNameList(userId).collect {
                emit(it)
            }
        }

    override suspend fun insertPokemonFavorite(pokemon: Pokemon, userId: String) =
        pokemonDao.insertPokemonFavorite(pokemon.asFavoriteEntity(userId))

    override suspend fun deletePokemonFavorite(pokemon: Pokemon, userId: String) =
        pokemonDao.deletePokemonFavorite(pokemon.asFavoriteEntity(userId))
}