package com.z1.pokedex.feature.favorites.data.repository

import com.z1.pokedex.core.database.dao.PokemonFavoriteDao
import com.z1.pokedex.feature.favorites.data.datasource.local.PokemonFavoriteLocalDataSource
import com.z1.pokedex.feature.favorites.domain.repository.PokemonFavoriteRepository
import com.z1.pokedex.feature.favorites.domain.repository.mapper.asFavoriteEntity
import com.z1.pokedex.feature.favorites.domain.repository.mapper.asModel
import com.z1.pokedex.feature.home.domain.model.Pokemon
import kotlinx.coroutines.flow.flow

class PokemonFavoriteRepositoryImpl(
    private val pokemonFavoriteDao: PokemonFavoriteDao,
    private val pokemonFavoriteLocalDataSource: PokemonFavoriteLocalDataSource
) : PokemonFavoriteRepository {
    override suspend fun getPokemonFavorites(userId: String) =
        flow {
            pokemonFavoriteLocalDataSource.getPokemonFavorites(userId)
                .collect {
                    emit(it.asModel())
                }
        }

    override suspend fun insertPokemonFavorite(pokemon: Pokemon, userId: String) =
        pokemonFavoriteDao.insertPokemonFavorite(pokemon.asFavoriteEntity(userId))

    override suspend fun deletePokemonFavorite(pokemon: Pokemon, userId: String) =
        pokemonFavoriteDao.deletePokemonFavorite(pokemon.asFavoriteEntity(userId))
}